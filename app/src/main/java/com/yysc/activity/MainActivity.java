package com.yysc.activity;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yysc.activity.databinding.ActivityMainBinding;
import com.yysc.activity.dragger.DaggerAppComponent;
import com.yysc.activity.dragger.mainModule;
import com.yysc.commomlibary.base.BaseActivity;

import javax.inject.Inject;


public class MainActivity
        extends BaseActivity
{

    private static final String TAG = "MainActivity";
    private EditText            mEdit;
    private TextView            mText;
    private RelativeLayout      mActivityMain;
    @Inject
    TestViewModel       mViewModel;

    private ActivityMainBinding binding;

    @Override
    public void initView() {

        mEdit = (EditText) findViewById(R.id.edit);
        mText = (TextView) findViewById(R.id.text);
        mActivityMain = (RelativeLayout) findViewById(R.id.activity_main);
    }

    @Override
    public void initData() {
        //        mViewDataBinding.
        binding = (ActivityMainBinding) mDataBinding;
//        mDataBinding.setVariable();
//        mViewModel = getViewModel();
        DaggerAppComponent.builder().build().inject(this);
//        binding.setViewModel(mViewModel);
//        mViewModel.changeEditText(mEdit);
        //        mEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mViewModel.changeEditText(mEdit);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getContentView() {
//         ActivityMainBinding bing = DataBindingUtil.setContentView(this, R.layout.activity_main);
        return R.layout.activity_main;
    }


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
//        super.onCreate(savedInstanceState);
//
//    }
}
