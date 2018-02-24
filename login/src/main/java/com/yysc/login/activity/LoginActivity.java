package com.yysc.login.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import com.yysc.commomlibary.base.BaseActivity;
import com.yysc.login.R;
import com.yysc.login.dagger.DaggerLoginComponent;
import com.yysc.login.dagger.LoginModule;
import com.yysc.login.databinding.ActivityLoginBinding;
import com.yysc.login.util.Utils;
import com.yysc.login.viewmodel.LoginViewModel;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by kj00037 on 2017/12/29.
 */

public class LoginActivity
        extends BaseActivity
        implements View.OnClickListener
{
    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding mBinding;
    @Inject
    LoginViewModel mViewModel;
    private LoginModule mLoginModule;
    /** 加载补丁 */
    private Button      mLoadPatch;
    /** 删除补丁 */
    private Button      mDeletaPatch;
    /** 重启 */
    private Button      mKillPid;
    private Button      mShow;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    @Override
    public void initView() {
        mBinding = (ActivityLoginBinding) mDataBinding;
        //        Log.e("123", "initView: "+Environment.getExternalStorageDirectory().getAbsolutePath());
        mLoadPatch = (Button) findViewById(R.id.loadPatch);
        mLoadPatch.setOnClickListener(this);
        mDeletaPatch = (Button) findViewById(R.id.deletaPatch);
        mDeletaPatch.setOnClickListener(this);
        mKillPid = (Button) findViewById(R.id.killPid);
        mKillPid.setOnClickListener(this);
        mShow = (Button) findViewById(R.id.show);
        mShow.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //        DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build().inject(this);
        mLoginModule = new LoginModule(this);
        DaggerLoginComponent component = (DaggerLoginComponent) DaggerLoginComponent.builder().loginModule(mLoginModule).build();
        mLoginModule.setComponent(component);
        component.inject(this);
        mBinding.setViewModel(mViewModel);
        verifyStoragePermissions(this);
    }

    @Override
    public void initEvent() {

    }


    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setBackground(false);

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                                              REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.setBackground(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
               break;
            case R.id.loadPatch:
//                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
//                Toast.makeText(this, "loadPatch", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deletaPatch:
                Tinker.with(getApplicationContext()).cleanPatch();
//                Toast.makeText(this,"deletaPatch",Toast.LENGTH_SHORT).show();
                break;
            case R.id.killPid:
                ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            case R.id.show:
//                Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                showInfo(LoginActivity.this);
                break;
        }
    }

    public boolean showInfo(Context context) {
        // add more Build Info
        final StringBuilder sb = new StringBuilder();
        Tinker tinker = Tinker.with(getApplicationContext());
        if (tinker.isTinkerLoaded()) {
            sb.append(String.format("[patch is loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(
                    ShareConstants.TINKER_ID)));
            sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchMessage")));
            sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.getTinkerRomSpace()));

        } else {
            sb.append(String.format("[patch is not loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(getApplicationContext())));
        }
        sb.append(String.format("[BaseBuildInfo Message] %s \n", BaseBuildInfo.TEST_MESSAGE));

        final TextView v = new TextView(context);
        v.setText(sb);
        v.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setTextColor(0xFF000000);
        v.setTypeface(Typeface.MONOSPACE);
        final int padding = 16;
        v.setPadding(padding, padding, padding, padding);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        return true;
    }

}
