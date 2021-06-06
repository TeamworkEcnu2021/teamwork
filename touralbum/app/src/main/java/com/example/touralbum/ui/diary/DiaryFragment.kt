package com.example.touralbum.ui.diary

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touralbum.R
import com.example.touralbum.ui.diary.note.*

class DiaryFragment : Fragment() {

    private lateinit var noteListView: RecyclerView
    private val noteList = ArrayList<NoteInfo>()
    private lateinit var mListAdapter: ListAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_dairy_list,container,false)

        val toolbar = root.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_diary_list)
        toolbar.inflateMenu(R.menu.diary_list_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.add_diary -> {
                    val intent = Intent(activity, CreateDairy::class.java)
                    startActivity(intent)
                }
            /*
                R.id.back_to_dairy->{
                    val title = "提示"
                    AlertDialog.Builder(requireContext())
                        .setTitle(title)
                        .setMessage("确定要退出吗?")
                        .setPositiveButton(R.string.btn_confirm) {
                            dialog: DialogInterface?, which: Int ->
                            onDestroy()
                        }
                        .setNegativeButton(R.string.btn_cancel) { dialog: DialogInterface?, which: Int -> }
                        .create().show()
                }*/
            }
            true
        }

        dbHelper = NoteDataBaseHelper(activity, "MyNote.db", null, 1)
        initView(root)
        //setListener()
        val intent = requireActivity().intent
        if (intent != null) {
            getNoteList()
            mListAdapter.notifyDataSetChanged()
        }
        return root
    }

    override fun onResume() {
        noteList.clear()
        getNoteList()
        mListAdapter.notifyDataSetChanged()
        super.onResume()
    }

    //初始化视图
    private fun initView(root:View) {
        val layoutManager = GridLayoutManager(activity,1)
        noteListView = root.findViewById(R.id.note_list)
        noteListView.layoutManager = layoutManager
        //获取noteList
        getNoteList()
        mListAdapter = ListAdapter(noteList)
        noteListView.adapter = mListAdapter
    }

    /*
    //设置监听器
    private fun setListener() {
        //设置监听并跳转到创建日志页面
        noteListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val noteInfo = noteList[position]
                val intent = Intent()
                val bundle = Bundle()
                bundle.putSerializable("noteInfo", noteInfo)
                intent.putExtras(bundle)
                intent.setClass(requireActivity(), CreateDairy::class.java)
                startActivity(intent)
            }
        noteListView.onItemLongClickListener =
            OnItemLongClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val noteInfo = noteList[position]
                val title = "警告"
                AlertDialog.Builder(requireActivity())
                    .setTitle(title)
                    .setMessage("确定要删除吗?")
                    .setPositiveButton(R.string.btn_confirm) { dialog: DialogInterface?, which: Int ->
                        Note.deleteNote(dbHelper, noteInfo.id!!.toInt())
                        noteList.removeAt(position)
                        mListAdapter.refreshDataSet()
                        Toast.makeText(activity, "删除成功！", Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton(R.string.btn_cancel) { dialog: DialogInterface?, which: Int -> }
                    .create().show()
                true
            }
    }
    */

    //从数据库中读取所有笔记，封装成List<NoteInfo>
    private fun getNoteList() {
        noteList.clear()
        val allNotes: Cursor = Note.getAllNotes(dbHelper)
        allNotes.moveToFirst()
        while (!allNotes.isAfterLast) {
            val noteInfo = NoteInfo()
            noteInfo.id =
                allNotes.getString(allNotes.getColumnIndex(Note._id))
            noteInfo.title =
                allNotes.getString(allNotes.getColumnIndex(Note.title))
            noteInfo.content =
                allNotes.getString(allNotes.getColumnIndex(Note.content))
            noteInfo.date =
                allNotes.getString(allNotes.getColumnIndex(Note.time))
            noteList.add(noteInfo)
            allNotes.moveToNext()
        }
    }


    companion object {

        //给其他类提供dbHelper
        lateinit var dbHelper: NoteDataBaseHelper
            private set
    }
}