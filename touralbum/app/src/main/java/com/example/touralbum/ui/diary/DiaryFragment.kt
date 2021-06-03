package com.example.touralbum.ui.diary

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.*
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.touralbum.R
import com.example.touralbum.ui.diary.note.ListAdpter
import com.example.touralbum.ui.diary.note.Note
import com.example.touralbum.ui.diary.note.NoteDataBaseHelper
import com.example.touralbum.ui.diary.note.NoteInfo
import kotlin.collections.ArrayList

class DiaryFragment : Fragment() {
    private var noteListView: ListView? = null
    private val noteList: MutableList<NoteInfo> = ArrayList()
    private var mListAdapter: ListAdpter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dairy_list,container,false)
        dbHelper = NoteDataBaseHelper(activity, "MyNote.db", null, 1)
        initView()
        setListener()
        val intent = requireActivity().intent
        if (intent != null) {
            getNoteList()
            mListAdapter!!.refreshDataSet()
        }
        return root
    }

    //初始化视图
    private fun initView() {
        noteListView = view?.findViewById(R.id.note_list)
        //获取noteList
        getNoteList()
        mListAdapter = ListAdpter(activity, noteList as ArrayList<NoteInfo>)
        noteListView!!.setAdapter(mListAdapter)
    }

    //设置监听器
    private fun setListener() {
        //设置监听并跳转到创建日志页面
        noteListView!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val noteInfo = noteList[position]
                val intent = Intent()
                val bundle = Bundle()
                bundle.putSerializable("noteInfo", noteInfo)
                intent.putExtras(bundle)
                intent.setClass(requireActivity(), CreateDairy::class.java)
                startActivity(intent)
            }
        noteListView!!.onItemLongClickListener =
            OnItemLongClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val noteInfo = noteList[position]
                val title = "警告"
                AlertDialog.Builder(requireActivity())
                    .setTitle(title)
                    .setMessage("确定要删除吗?")
                    .setPositiveButton(R.string.btn_confirm) { dialog: DialogInterface?, which: Int ->
                        Note.deleteNote(dbHelper, noteInfo.id!!.toInt())
                        noteList.removeAt(position)
                        mListAdapter!!.refreshDataSet()
                        Toast.makeText(activity, "删除成功！", Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton(R.string.btn_cancel) { dialog: DialogInterface?, which: Int -> }
                    .create().show()
                true
            }
    }

    //从数据库中读取所有笔记，封装成List<NoteInfo>
    private fun getNoteList() {
        noteList.clear()
        val allNotes: Cursor = Note.Companion.getAllNotes(dbHelper)
        allNotes.moveToFirst()
        while (!allNotes.isAfterLast) {
            val noteInfo = NoteInfo()
            noteInfo.id =
                allNotes.getString(allNotes.getColumnIndex(Note.Companion._id))
            noteInfo.title =
                allNotes.getString(allNotes.getColumnIndex(Note.Companion.title))
            noteInfo.content =
                allNotes.getString(allNotes.getColumnIndex(Note.Companion.content))
            noteInfo.date =
                allNotes.getString(allNotes.getColumnIndex(Note.Companion.time))
            noteList.add(noteInfo)
            allNotes.moveToNext()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.add("addDiary").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_diary -> {
                val intent = Intent(activity, CreateDairy::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        //重写返回按钮处理事件
        /*
              @Override
              public void onBackPressed() {
                  String title = "提示";
                  new AlertDialog.Builder(DairyList.this)
                          .setTitle(title)
                          .setMessage("确定要退出吗?")
                          .setPositiveButton(R.string.btn_confirm, (dialog, which) -> finish())
                          .setNegativeButton(R.string.btn_cancel, (dialog, which) -> {
                          }).create().show();
              }*/
        //给其他类提供dbHelper
        var dbHelper: NoteDataBaseHelper? = null
            private set
    }
}