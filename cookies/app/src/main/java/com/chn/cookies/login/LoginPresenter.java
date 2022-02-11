package com.chn.cookies.login;


import android.content.Intent;
import android.text.TextUtils;

import com.chn.cookies.MainActivity;

import com.chn.cookies.R;

/**
 * Created by EverGlow on 2018/8/14 14:25
 */
public    class LoginPresenter  {

//    @Inject
//    GsonRequest mGsonRequest;
  //  @Inject
    public LoginPresenter(LoginContract.model model, LoginContract.view rootView) {
        //super(model, rootView);
    }
    
    public void login(String account, String pwd) {

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            //mRootView.showMessage(MyApplication.APP.getResources().getString(R.string.register_input_empty));
        } else {
            //mRootView.launchActivity(new Intent(MyApplication.APP, MainActivity.class));
           // mRootView.killMyself();
            
          /*  HashMap<String, String> map = new HashMap<>();
            map.put(NetWorkConstant.appUserAccount, account);
            map.put("appUserId","11");
            map.put(NetWorkConstant.appPassword, Tools.md5(Tools.md5(pwd) + "fht"));

            mGsonRequest.function(NetWorkConstant.app_login, map, EntityBase.class, new CallBack<EntityBase>() {
                @Override
                public void onResponse(EntityBase result) {
                    if (result.getResult() == 0) {
                        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
                        preferences.setModel(result.getAppUsers());
                        mRootView.launchActivity(new Intent(MyApplication.APP, MainActivity.class));
                        mRootView.killMyself();
                    }
                    mRootView.showMessage(result.getMsg());
                }

                @Override
                public void onFailure(String error) {
                    ToastUtil.showMessage(error);
                }
            });*/
        }
    }

    public void onDestroy() {
       // super.onDestroy();
//        this.mGsonRequest = null;
    }
}
