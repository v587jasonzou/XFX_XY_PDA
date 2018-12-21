package jx.yunda.com.terminal.modules.tpprocess;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.modules.api.TicketApi;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.ImageDao;
import jx.yunda.com.terminal.modules.tpprocess.RealmDb.TicketSubmitDao;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.ImageResponsBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TicketListPresenter extends BasePresenter<ITicketList> {
    public TicketListPresenter(ITicketList view) {
        super(view);
    }

    /**
     * @param start
     * @param limit
     * @param str
     * @param type 0：刷新； !0:加载更多
     */
    public void getTicketList(int start, int limit, String str, final int type) {
        RequestFactory.getInstance().createApi(TicketApi.class).getTicketList(str, start + "", limit + "").enqueue(new Callback<BaseBean<List<TicketInfoBean>>>() {
            @Override
            public void onResponse(Call<BaseBean<List<TicketInfoBean>>> call, Response<BaseBean<List<TicketInfoBean>>> response) {
                BaseBean<List<TicketInfoBean>> bean = response.body();
                if (bean.isSuccess()) {
                    List<TicketInfoBean> list = bean.getRoot();
                    if (type == 0) {
                        getViewRef().OnTicketListLoadSuccess(list);
                    } else {
                        getViewRef().OnTicketListLoadMoreSuccess(list);
                    }
                } else {
                    getViewRef().OnLoadFail("获取提票记录失败，请重新访问");
                }

            }

            @Override
            public void onFailure(Call<BaseBean<List<TicketInfoBean>>> call, Throwable t) {
                getViewRef().OnLoadFail("获取提票记录失败，请重新访问" + t.getMessage());
            }
        });
    }

    public int ImageAll = 0;
    public int Imagestart = 0;
    public int TicketAll = 0;
    public int Ticketstart = 0;
    public int Ticketsize = 0;
    public int Imageposition = 0;
    public ImageDao imagedao;
    public void updateImages( int position) {
        Imageposition = position;
        imagedao = imagedaos.get(position);
        String base64 = Utils.getBase64StringFromImg(imagedao.getFilePath());
        RequestFactory.getInstance().createApi(TicketApi.class).upLoadImage(base64, ".jpg").enqueue(new Callback<ImageResponsBean>() {
            @Override
            public void onResponse(Call<ImageResponsBean> call, Response<ImageResponsBean> response) {
                ImagesSize++;
                final ImageResponsBean beans = response.body();
                if (bean != null && beans.isSuccess()) {
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(final Subscriber<? super Integer> subscriber) {
                            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    ImageDao image = realm.where(ImageDao.class)
                                            .equalTo("idx", Imageposition).equalTo("planIdx", bean.getIdx())
                                            .findFirst();
                                    image.setUpLoad(true);
                                    image.setFileBackPath(beans.getFilePath());
                                    subscriber.onNext(1);
                                }
                            });

                        }
                    }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Integer>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Imagestart++;
                                    if (Imagestart < ImageAll) {
                                        Observable.create(new Observable.OnSubscribe<Integer>() {
                                            @Override
                                            public void call(Subscriber<? super Integer> subscriber) {
                                                updateImages(Imagestart);
                                            }
                                        }).subscribeOn(Schedulers.io()).subscribe();
                                    }else {
                                        if(ImagesSize==ImageAll){
                                            updateTicke(false);
                                        }
                                    }
                                }

                                @Override
                                public void onNext(Integer integer) {
                                    Imagestart++;
                                    if (Imagestart < ImageAll) {
                                        Observable.create(new Observable.OnSubscribe<Integer>() {
                                            @Override
                                            public void call(Subscriber<? super Integer> subscriber) {
                                                updateImages(Imagestart);
                                            }
                                        }).subscribeOn(Schedulers.io()).subscribe();
                                    }else {
                                        Observable.create(new Observable.OnSubscribe<Integer>() {
                                            @Override
                                            public void call(Subscriber<? super Integer> subscriber) {
                                                RealmResults<ImageDao> daos = Realm.getDefaultInstance().where(ImageDao.class)
                                                        .equalTo("planIdx", bean.getIdx()).equalTo("isUpLoad", false)
                                                        .findAll();
                                                if (daos.size() > 0) {
                                                    subscriber.onNext(1);
                                                } else {
                                                    subscriber.onNext(0);
                                                }
                                            }
                                        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(Schedulers.io())
                                                .subscribe(new Observer<Integer>() {
                                                    @Override
                                                    public void onCompleted() {

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {

                                                    }

                                                    @Override
                                                    public void onNext(Integer integer) {
                                                        if (integer == 0) {
                                                            if(ImagesSize==ImageAll){
                                                                updateTicke(true);
                                                            }
                                                        } else {
                                                            if(ImagesSize==ImageAll){
                                                                updateTicke( false);
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });

                } else {
                    Imagestart++;
                    if (Imagestart < ImageAll) {
                        Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                updateImages(Imagestart);
                            }
                        }).subscribeOn(Schedulers.io()).subscribe();
                    }else {
                        if(ImagesSize==ImageAll){
                            updateTicke(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageResponsBean> call, Throwable t) {
                Imagestart++;
                if (Imagestart < ImageAll) {
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            updateImages(Imagestart);
                        }
                    }).subscribeOn(Schedulers.io()).subscribe();
                }else {
                    if(ImagesSize==ImageAll){
                        updateTicke(false);
                    }
                }
            }
        });
    }

    public void updateTicke( boolean is) {
        if (is) {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    ArrayList<Map<String, String>> list = new ArrayList<>();
                    List<ImageDao> daos = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(ImageDao.class)
                            .equalTo("planIdx",bean.getIdx()).findAll());
                    if (daos != null && daos.size() > 0) {
                        for (ImageDao dao : daos) {
                            Map<String, String> map = new HashMap<>();
                            map.put("filename", dao.getFilename());
                            map.put("filePath", dao.getFileBackPath());
                            list.add(map);
                        }
                    }
                    String jsonstr2 = JSON.toJSONString(list);
                    subscriber.onNext(jsonstr2);
                }
            }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(Schedulers.io())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            long operatorid = bean.getOperatorId();
                            ArrayList<Map<String, Object>> list2 = new ArrayList<>();
                            Map<String, Object> map1 = new HashMap<>();
                            map1.put("trainTypeIDX", bean.getTrainTypeIDX());
                            map1.put("trainNo", bean.getTrainNo());
                            map1.put("trainTypeShortName", bean.getTrainTypeShortName());
                            map1.put("faultID", "");
                            map1.put("faultName", "");
                            map1.put("faultDesc", bean.getFaultDesc());
                            map1.put("faultFixPlaceIDX", bean.getFaultFixPlaceIDX());
                            map1.put("fixPlaceFullCode", bean.getFixPlaceFullCode());
                            map1.put("fixPlaceFullName", bean.getFixPlaceFullName());
                            map1.put("faultOccurDate", bean.getFaultOccurDate());

                            map1.put("professionalTypeIdx", "");
                            map1.put("professionalTypeName", "");
                            map1.put("professionalTypeSeq", "");
                            map1.put("reasonAnalysisID", bean.getReasonAnalysisID());
                            map1.put("reasonAnalysis", bean.getReasonAnalysis());
                            map1.put("resolutionContent", bean.getResolutionContent());
                            map1.put("type", bean.getType());
                            list2.add(map1);
                            String jsonstr1 = JSON.toJSONString(list2);
                            RequestFactory.getInstance().createApi(TicketApi.class).saveTicket(operatorid, jsonstr1, s).enqueue(new Callback<BaseBean>() {
                                @Override
                                public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                                    BaseBean beans = response.body();
                                    Ticketstart++;
                                    Ticketsize++;
                                    if (beans != null) {
                                        if (beans.isSuccess()) {
                                            Observable.create(new Observable.OnSubscribe<Integer>() {
                                                @Override
                                                public void call(final Subscriber<? super Integer> subscriber) {
                                                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                                        @Override
                                                        public void execute(Realm realm) {
                                                            realm.where(TicketSubmitDao.class)
                                                                    .equalTo("idx", bean.getIdx()).findAll().deleteAllFromRealm();
                                                          subscriber.onNext(1);
                                                        }
                                                    });
                                                }
                                            }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Observer<Integer>() {
                                                        @Override
                                                        public void onCompleted() {

                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {

                                                        }

                                                        @Override
                                                        public void onNext(Integer integer) {
                                                            if(Ticketstart<TicketAll){
                                                                Observable.create(new Observable.OnSubscribe<Integer>() {
                                                                    @Override
                                                                    public void call(Subscriber<? super Integer> subscriber) {
                                                                        Upload(AllBeans.get(Ticketstart));
                                                                    }
                                                                }).subscribeOn(Schedulers.io()).subscribe();
                                                            }else {
                                                                if (Ticketsize == TicketAll) {
                                                                    getViewRef().OnTicketSubmitSuccess(0);
                                                                }
                                                            }
                                                        }
                                                    });

                                        } else {
                                            if(Ticketstart<TicketAll){
                                                Observable.create(new Observable.OnSubscribe<Integer>() {
                                                    @Override
                                                    public void call(Subscriber<? super Integer> subscriber) {
                                                        Upload(AllBeans.get(Ticketstart));
                                                    }
                                                }).subscribeOn(Schedulers.io()).subscribe();
                                            }else {
                                                if (Ticketsize == TicketAll) {
                                                    getViewRef().OnTicketSubmitSuccess(0);
                                                }
                                            }
                                        }
                                    } else {
                                        if(Ticketstart<TicketAll){
                                            Observable.create(new Observable.OnSubscribe<Integer>() {
                                                @Override
                                                public void call(Subscriber<? super Integer> subscriber) {
                                                    Upload(AllBeans.get(Ticketstart));
                                                }
                                            }).subscribeOn(Schedulers.io()).subscribe();
                                        }else {
                                            if (Ticketsize == TicketAll) {
                                                getViewRef().OnTicketSubmitSuccess(0);
                                            }
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<BaseBean> call, Throwable t) {
                                    Ticketstart++;
                                    Ticketsize++;
                                    if (Ticketstart == TicketAll) {
                                        getViewRef().OnTicketSubmitSuccess(0);
                                    }
                                }
                            });
                        }
                    });

        } else {
            Ticketstart++;
            Ticketsize++;
            if(Ticketstart<TicketAll){
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        Upload(AllBeans.get(Ticketstart));
                    }
                }).subscribeOn(Schedulers.io()).subscribe();
            }else {
                if (Ticketsize == TicketAll) {
                    getViewRef().OnTicketSubmitSuccess(0);
                }
            }

        }
    }
    public TicketInfoBean bean;
    public List<ImageDao> imagedaos = new ArrayList<>();
    public int ImagesSize = 0;
    public void Upload( TicketInfoBean bean1) {
        bean = bean1;
        ImagesSize = 0;
        Observable.create(new Observable.OnSubscribe<List<ImageDao>>() {
            @Override
            public void call(final Subscriber<? super List<ImageDao>> subscriber) {
                imagedaos.clear();
                List<ImageDao> mlist = Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance()
                        .where(ImageDao.class).equalTo("planIdx", bean.getIdx()).equalTo("isUpLoad", false)
                        .findAll());
                if(mlist!=null&&mlist.size()>0){
                    imagedaos.addAll(mlist) ;
                }
                if (imagedaos != null && imagedaos.size() > 0) {
                    for (int i = 0; i < imagedaos.size(); i++) {
                        imagedaos.get(i).setIdx(i);
                    }
                    subscriber.onNext(imagedaos);
                }else {
                    subscriber.onNext(null);
                }


            }
        }).subscribeOn(Schedulers.from(JXApplication.getExecutor())).observeOn(Schedulers.io())
                .subscribe(new Observer<List<ImageDao>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ImageDao> s) {
                        if (s != null && s.size() > 0) {
                            Imagestart = 0;
                            ImageAll = s.size();
                            updateImages(0);
                        }else {
                            updateTicke(true);
                        }
                    }
                });
    }

    public List<TicketInfoBean> UnUploads = new ArrayList<>();
    public List<TicketInfoBean> AllBeans = new ArrayList<>();
    public void UploadTickets(List<TicketInfoBean> list) {
        UnUploads.clear();
        AllBeans.clear();
        if (list.size() > 0) {
            AllBeans.addAll(list);
            Ticketsize = 0;
            TicketAll = AllBeans.size();
            Ticketstart = 0;
            Upload(AllBeans.get(0));
        }
    }
}
