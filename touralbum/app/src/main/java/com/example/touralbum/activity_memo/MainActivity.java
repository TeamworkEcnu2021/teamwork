package com.example.touralbum.activity_memo;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touralbum.R;
import com.example.touralbum.base_memo.BaseActivity;
import com.example.touralbum.base_memo.BaseHandler;
import com.example.touralbum.base_memo.EventRecyclerViewAdapter;
import com.example.touralbum.entity_memo.Event;
import com.example.touralbum.exception_memo.MemoException;
import com.example.touralbum.manager_memo.ClockManager;
import com.example.touralbum.manager_memo.EventManager;
import com.example.touralbum.service_memo.ClockService;
import com.example.touralbum.util_memo.AlertDialogUtil;
import com.example.touralbum.util_memo.AppUtil;
import com.example.touralbum.util_memo.StringUtil;
import com.example.touralbum.util_memo.ToastUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements BaseHandler.HandlerResultCallBack {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    private EventRecyclerViewAdapter mAdapter;
    private EventManager mEventManger = EventManager.getInstance();
    private ClockManager mClockManager = ClockManager.getInstance();
    private BaseHandler mBaseHandler = new BaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mAdapter = new EventRecyclerViewAdapter(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mSearchView.getLayoutParams();
        //将文字内容略微下移，SearchView  bug
        params.bottomMargin = -3;
        mSearchView.setLayoutParams(params);
        mSearchView.onActionViewExpanded();
        initSearchView();
    }

    private void initSearchView() {
        //一处searchView进入屏幕时候的焦点
        mSearchView.clearFocus();
        Class<? extends SearchView> aClass = mSearchView.getClass();
        try {
            //去掉SearchView自带的下划线
            Field mSearchPlate = aClass.getDeclaredField("mSearchPlate");
            mSearchPlate.setAccessible(true);
            View o = (View) mSearchPlate.get(mSearchView);
            o.setBackgroundColor(getColor(R.color.transparent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //隐藏键盘
        AppUtil.hideSoftInput(this, mSearchView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initData() {
        //设置数据源，适配器等等
        mAdapter.setDatabases(mEventManger.findAll());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void setListener() {
       mAdapter.setOnItemClickListener(mOnItemClickListener);
       mSearchView.setOnQueryTextListener(mQueryListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity_memo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //判断是点击了那个按钮，删除，添加
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent();
            intent.setClass(this, EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EXTRA_IS_ADD_EVENT, true);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_delete) {
            if (mAdapter.getIsDeleteMode()) {
                //删除数据
                if (mAdapter.getSelectedEventIds().size() == 0) {
                    ToastUtil.showToastShort(R.string.no_event_selected_msg);
                } else {
                    int msg = mAdapter.getSelectedEventIds().size() == 1 ? R.string.delete_event_msg : R.string.delete_events_msg;
                    AlertDialogUtil.showDialog(this, msg, mConfirmListener);
                }
            } else {
                mAdapter.setDeleteMode(true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private EventRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new EventRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (!mAdapter.getIsDeleteMode()) {
                //跳屏，此时为查看详情，不是编辑状态
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EventDetailActivity.class);
                intent.putExtra(EventDetailActivity.EXTRA_IS_EDIT_EVENT, false);
                intent.putExtra(EventDetailActivity.EXTRA_EVENT_DATA, mAdapter.getDatabases().get(position));
                startActivity(intent);
            }
        }

        @Override
        public void onItemLongClick(View view, int position) {
            ToastUtil.showToastShort("Long clicked");
            mAdapter.setDeleteMode(true);
        }
    };

    private DialogInterface.OnClickListener mConfirmListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mEventManger.setDeletedIds(mAdapter.getSelectedEventIds());
            mEventManger.removeEvents(mBaseHandler, mEventManger.getDeletedIds());
        }
    };

    /**
     * 从编辑屏幕回来时调用该方法，做数据更新
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mAdapter.setDatabases(mEventManger.getEvents());
    }

    private SearchView.OnQueryTextListener mQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            //做本地查询
            if (!StringUtil.isBlank(newText)) {
                List<Event> events = new ArrayList<>();
                for (Event event : mAdapter.getDatabases()) {
                    if (event.getmTitle().contains(newText)) {
                        events.add(event);
                    }
                }
                mAdapter.setDatabases(events);
            } else {
                mAdapter.setDatabases(mEventManger.getEvents());
            }
            return true;
        }
    };

    /**
     * handler处理成功的回调函数
     * @link com.jsbintask@gmail.com.memo.base.BaseHandler
     */
    @Override
    public void handlerSuccess(Message msg) {
        ToastUtil.showToastShort(R.string.delete_successful);
        for (PendingIntent pendingIntent : buildIntent(mEventManger.getDeletedIds())) {
            mClockManager.cancelAlarm(pendingIntent);
        }
        mAdapter.setDatabases(mEventManger.findAll());
    }
    /**
     * 处理失败的回调函数
     */
    @Override
    public void handlerFailed(MemoException e) {
        ToastUtil.showToastShort(R.string.delete_failed);
        mAdapter.setDeleteMode(false);
    }

    private List<PendingIntent> buildIntent(List<Integer> ids) {
        List<PendingIntent> pendingIntents = new ArrayList<>();
        for (Integer id : ids) {
            Intent intent = new Intent();
            intent.putExtra(ClockService.EXTRA_EVENT_ID, id);
            intent.setClass(this, ClockService.class);

            pendingIntents.add(PendingIntent.getService(this, 0x001, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
        return pendingIntents;
    }
}
