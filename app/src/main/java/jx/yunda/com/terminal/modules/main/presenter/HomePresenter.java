package jx.yunda.com.terminal.modules.main.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseResultEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.modules.main.model.FirstFuncBean;
import jx.yunda.com.terminal.modules.main.model.MenuConfig;
import jx.yunda.com.terminal.modules.main.model.PurviewMenu;
import jx.yunda.com.terminal.modules.main.model.SecondFuncBean;
import jx.yunda.com.terminal.modules.main.model.TodoJob;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.modules.api.HomeApi;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class HomePresenter extends BasePresenter<IHome> {
    public HomePresenter(IHome view) {
        super(view);
    }

    /**
     * <li>说明：获取权限菜单
     * <li>创建人：zhubs
     * <li>创建日期：2018年5月8日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     */
    public void getPurviewMenu() {
//        if (SysInfo.menus != null) {
//            getViewRef().setMenuAdapterData();
//            return;
//        }
        req(RequestFactory.getInstance().createApi(HomeApi.class).getPurviewMenu(""),true, new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                BaseResultEntity<FirstFuncBean> rst = JSON.parseObject(result, new TypeReference<BaseResultEntity<FirstFuncBean>>() {
                });
                List<FirstFuncBean> menuList = rst.getRoot();
                if (null == menuList || menuList.size() < 1) {
                    ToastUtil.toastShort("系统尚未分配功能权限给你，\n请尽快联系管理员为你授权！");
                    return;
                }
                SysInfo.menus = new ArrayList<>();
                for(int i = 0;i<menuList.size();i++){
                    SecondFuncBean bean = new SecondFuncBean();
                    bean.setFuncname(menuList.get(i).getFuncgroupname());
                    bean.setFirst(true);
                    SysInfo.menus.add(bean);
                    SysInfo.menus.addAll(menuList.get(i).getFunctionChildList());
                }
//                SysInfo.menus.clear();
//                PurviewMenu menu = new PurviewMenu();
//                menu.setFuncname("配件检修");
//                menu.setFunccode("pjjx");
//                PurviewMenu menu2= new PurviewMenu();
//                menu2.setFuncname("质量检查");
//                menu2.setFunccode("zljc");
//                PurviewMenu menu3 = new PurviewMenu();
//                menu3.setFuncname("复检提票");
//                menu3.setFunccode("fjtp");
//                PurviewMenu menu4 = new PurviewMenu();
//                menu4.setFuncname("机车检修");
//                menu4.setFunccode("jcjx");
//                SysInfo.menus.add(menu);
//                SysInfo.menus.add(menu2);
//                SysInfo.menus.add(menu2);
//                SysInfo.menus.add(menu3);
//                SysInfo.menus.add(menu4);
                initMenuData();
                //设置页面
                getViewRef().setMenuAdapterData();
            }

            @Override
            public void onError(ApiException e) {
                LogUtil.e("获取登录用户权限报错", e.getMessage());
            }
        });
    }

    // 初始化菜单数据
    public void initMenuData() {
        if (SysInfo.menus == null) return;
        SysInfo.trainMenus = new ArrayList<>();
        SysInfo.partMenus = new ArrayList<>();
        // 绑定图标和背景颜色，及跳转活动
        for (SecondFuncBean menu : SysInfo.menus) {
            menu.setMenuConfig(getMenuConfiguration(menu.getFuncname()));
            if (menu.getMenuConfig() == null&&!menu.getFirst())
                continue;
            SysInfo.partMenus.add(menu);

        }
    }

    // 获取菜单图标
    public MenuConfig getMenuConfiguration(String key) {
        if (SysInfo.menuConfigMap.containsKey(key))
            return SysInfo.menuConfigMap.get(key);
        return null;
    }

    //获取任务代办数量
    public void getTodoJobs() {
        req(RequestFactory.getInstance().createApi(HomeApi.class).getTodoJobCount(),false, new HttpOnNextListener() {
            @Override
            public void onNext(String result, String mothead) {
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.get("list") != null) {
                    List<TodoJob> todoJobs = JSON.parseObject(JSON.toJSONString(jsonObject.get("list")), new TypeReference<ArrayList<TodoJob>>() {
                    });
                    if (todoJobs != null && todoJobs.size() > 0) {
                        for (SecondFuncBean menu : SysInfo.menus) {
                            TodoJob existJob = null;
                            for (TodoJob job : todoJobs) {
                                if (menu.getFuncname().equals(job.getJobType())) {
                                    existJob = job;
                                    break;
                                }
                            }
//                            menu.setTodoJob(existJob);
                        }
                        getViewRef().recyclerAdapterNotifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(ApiException e) {
                return;
            }
        });
    }
}


