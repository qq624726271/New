package com.chenxiayan.newapp.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chenxiayan.newapp.Adapter.Adapter;
import com.chenxiayan.newapp.R;
import com.chenxiayan.newapp.Util.HttpUtil;
import com.chenxiayan.newapp.Util.Utility;
import com.chenxiayan.newapp.gson.News;
import com.chenxiayan.newapp.gson.NewsList;
import com.chenxiayan.newapp.model.Title;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyItemClickListener{
    /***
     * 方便标记点击的选项
     */
    private static final int  ITEM_SOCIETY= 1;
    private static final int  ITEM_COUNTY= 2;
    private static final int  ITEM_INTERNATION= 3;
    private static final int  ITEM_FUN= 4;
    private static final int  ITEM_SPORT= 5;
    private static final int  ITEM_NBA= 6;
    private static final int  ITEM_FOOTBALL= 7;
    private static final int  ITEM_TECHNOLOGY= 8;
    private static final int  ITEM_WORK= 9;
    private static final int  ITEM_APPLE= 10;
    private static final int  ITEM_WAR= 11;
    private static final int  ITEM_INTERNET= 12;
    private static final int  ITEM_TREVAL= 13;
    private static final int  ITEM_HEALTH= 14;
    private static final int  ITEM_STRANGE= 15;
    private static final int  ITEM_LOOKER= 16;
    private static final int  ITEM_VR= 17;
    private static final int  ITEM_IT= 18;


    private List<Title> mTitleList = new ArrayList<Title>();
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ListView listView;


    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("社会新闻");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);

        listView = (ListView)findViewById(R.id.list_view);
        adapter = new Adapter(this,R.layout.recycleview_item, mTitleList);
        //绑定适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            Intent intent = new Intent(MainActivity.this, ContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Title title = mTitleList.get(position);
                intent.putExtra("title",toolbar.getTitle());
                intent.putExtra("url",title.getUrl());
                startActivity(intent);
            }
        });


        /***
         * 下拉菜单刷新
         */
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);    //下拉进度条颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                int itemname = parseString((String)(toolbar.getTitle()));
                requestNew(itemname);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_society);   //默认选中为社会新闻
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_society:
                        handleCurrentPage("社会新闻",ITEM_SOCIETY);
                        break;
                    case R.id.nav_country:
                        handleCurrentPage("国内新闻",ITEM_COUNTY);
                        break;
                    case R.id.nav_internation:
                        handleCurrentPage("国际新闻",ITEM_INTERNATION);
                        break;
                    case R.id.nav_fun:
                        handleCurrentPage("娱乐新闻",ITEM_FUN);
                        break;
                    case R.id.nav_sport:
                        handleCurrentPage("体育新闻",ITEM_SPORT);
                        break;
                    case R.id.nav_nba:
                        handleCurrentPage("NBA新闻",ITEM_NBA);
                        break;
                    case R.id.nav_football:
                        handleCurrentPage("足球新闻",ITEM_FOOTBALL);
                        break;
                    case R.id.nav_technology:
                        handleCurrentPage("科技新闻",ITEM_TECHNOLOGY);
                        break;
                    case R.id.nav_work:
                        handleCurrentPage("创业新闻",ITEM_WORK);
                        break;
                    case R.id.nav_apple:
                        handleCurrentPage("苹果新闻",ITEM_APPLE);
                        break;
                    case R.id.nav_war:
                        handleCurrentPage("军事新闻",ITEM_WAR);
                        break;
                    case R.id.nav_internet:
                        handleCurrentPage("移动互联",ITEM_INTERNET);
                        break;
                    case R.id.nav_travel:
                        handleCurrentPage("旅游资讯",ITEM_TREVAL);
                        break;
                    case R.id.nav_health:
                        handleCurrentPage("健康知识",ITEM_HEALTH);
                        break;
                    case R.id.nav_strange:
                        handleCurrentPage("奇闻异事",ITEM_STRANGE);
                        break;
                    case R.id.nav_looker:
                        handleCurrentPage("美女图片",ITEM_LOOKER);
                        break;
                    case R.id.nav_vr:
                        handleCurrentPage("VR科技",ITEM_VR);
                        break;
                    case R.id.nav_it:
                        handleCurrentPage("IT资讯",ITEM_IT);
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();    //当用户点击了菜单项 即将滑动菜单关闭
                return true;
            }
        });


    }

    private int parseString(String text) {
        if (text.equals("社会新闻")){
            return ITEM_SOCIETY;
        }
        if (text.equals("国内新闻")){
            return ITEM_COUNTY;
        }
        if (text.equals("国际新闻")){
            return ITEM_INTERNATION;
        }
        if (text.equals("娱乐新闻")){
            return ITEM_FUN;
        }
        if (text.equals("体育新闻")){
            return ITEM_SPORT;
        }
        if (text.equals("NBA新闻")){
            return ITEM_NBA;
        }
        if (text.equals("足球新闻")){
            return ITEM_FOOTBALL;
        }
        if (text.equals("科技新闻")){
            return ITEM_TECHNOLOGY;
        }
        if (text.equals("创业新闻")){
            return ITEM_WORK;
        }
        if (text.equals("苹果新闻")){
            return ITEM_APPLE;
        }
        if (text.equals("军事新闻")){
            return ITEM_WAR;
        }
        if (text.equals("移动互联")){
            return ITEM_INTERNET;
        }
        if (text.equals("旅游资讯")){
            return ITEM_TREVAL;
        }
        if (text.equals("健康知识")){
            return ITEM_HEALTH;
        }
        if (text.equals("奇闻异事")){
            return ITEM_STRANGE;
        }
        if (text.equals("美女图片")){
            return ITEM_LOOKER;
        }
        if (text.equals("VR科技")){
            return ITEM_VR;
        }
        if (text.equals("IT资讯")){
            return ITEM_IT;
        }
        return ITEM_SOCIETY;
    }

    /***
     * 点击navigation 展示不同的信息
     * @param text
     * @param item
     */
    private void handleCurrentPage(String text, int item) {
        if(!text.equals(toolbar.getTitle().toString())){
            toolbar.setTitle(text);
            requestNew(item);
            //刷新动画
            swipeRefresh.setRefreshing(true);
        }
    }

    /***
     * 发送请求查询
     * @param item
     */
    private void requestNew(int item) {
        String adress = response(item);
        Log.d(TAG, "requestNew:" + adress);
        HttpUtil.sendOkHttpRequest(adress, new Callback() {
            @Override

            //失败
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "新闻加载失败", Toast.LENGTH_SHORT).show();
            }

            /***
             * 在子线程中执行！！！注意！！！
             * @param call
             * @param response
             * @throws IOException
             */
            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final NewsList mNewlist = Utility.parseJsonWithGson(responseText);
                final int code = mNewlist.getCode();
                //code 表示返回成功
                if (code == 200) {
                    mTitleList.clear();
                    Log.d(TAG, "onResponse: "+code);
                    /***
                     * 成功并返回数据，添加到mTitleList中
                     */
                    for (News news : mNewlist.getNewslist()) {
                        Title title = new Title(news.getTitle(), news.getDescription(), news.getPicUrl(), news.getUrl());
                        Log.d(TAG, "onResponse: "+ news.getTitle());
                        mTitleList.add(title);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            //通知适配器发生改变
                            listView.setSelection(0);
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

            /***
             * 返回相应address  默认查找为social新闻
             * @param item
             * @return
             */
            public String response(int item) {
                String address = "http://api.tianapi.com/social/?key=aee011c73a8ca896fd9ad063d2b681e7&num=20&rand=1";
                switch (item) {
                    case ITEM_SOCIETY:
                        break;
                    case ITEM_COUNTY:
                        address = address.replaceAll("social", "guonei");
                        break;
                    case ITEM_INTERNATION:
                        address = address.replaceAll("social", "world");
                        break;
                    case ITEM_FUN:
                        address = address.replaceAll("social", "huabian");
                        break;
                    case ITEM_SPORT:
                        address = address.replaceAll("social", "tiyu");
                        break;
                    case ITEM_NBA:
                        address = address.replaceAll("social", "nba");
                        break;
                    case ITEM_FOOTBALL:
                        address = address.replaceAll("social", "football");
                        break;
                    case ITEM_TECHNOLOGY:
                        address = address.replaceAll("social", "keji");
                        break;
                    case ITEM_WORK:
                        address = address.replaceAll("social", "startup");
                        break;
                    case ITEM_APPLE:
                        address = address.replaceAll("social", "apple");
                        break;
                    case ITEM_WAR:
                        address = address.replaceAll("social", "military");
                        break;
                    case ITEM_INTERNET:
                        address = address.replaceAll("social", "mobile");
                        break;
                    case ITEM_TREVAL:
                        address = address.replaceAll("social", "travel");
                        break;
                    case ITEM_HEALTH:
                        address = address.replaceAll("social", "health");
                        break;
                    case ITEM_STRANGE:
                        address = address.replaceAll("social", "qiwen");
                        break;
                    case ITEM_LOOKER:
                        address = address.replaceAll("social", "meinv");
                        break;
                    case ITEM_VR:
                        address = address.replaceAll("social", "vr");
                        break;
                    case ITEM_IT:
                        address = address.replaceAll("social", "it");
                        break;
                    default:
                }
                return address;
            }

            /***
             * 选项栏 弹出DrawerLayout
             * @param item
             * @return
             */

            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        mDrawerLayout.openDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }

            /***
             * 点击Item
             * 跳转contentActivity
             * @param view
             * @param position
             */
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                Title title = mTitleList.get(position);
                intent.putExtra("title", toolbar.getTitle());
                intent.putExtra("uri", title.getUrl());
                startActivity(intent);
            }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            finish();
        }
    }
}

