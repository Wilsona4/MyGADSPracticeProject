package com.funcrib.gadsapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.funcrib.gadsapp.R;
import com.funcrib.gadsapp.view.fragment.ConfirmDialogFragment;
import com.funcrib.gadsapp.view.fragment.DiologaOkFragment;
import com.funcrib.gadsapp.view.fragment.ProgressDialogFragment;
import com.funcrib.gadsapp.view.fragment.SubmitResultDialogFragment;
import com.funcrib.gadsapp.model.SubmissionModel;
import com.funcrib.gadsapp.viewmodel.SubmitViewModel;

public class SubmitActivity extends AppCompatActivity implements ConfirmDialogFragment.OnClickListener {
    private SubmitViewModel viewModel;
    private SubmissionModel submissionModel = new SubmissionModel();
    private ProgressDialogFragment progressDialogFragment;
    private SubmitResultDialogFragment submitResultDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        DiologaOkFragment diologaOkFragment = DiologaOkFragment.newInstance(getString(R.string.fill_fields));
        ConfirmDialogFragment confirmDialogFragment = ConfirmDialogFragment.newInstance(getString(R.string.are_you_sure));
        progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.submitting));
        submitResultDialogFragment = SubmitResultDialogFragment.newInstance();

        viewModel = new ViewModelProvider(this).get(SubmitViewModel.class);
        viewModel.getStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer status) {
                if (status != SubmitViewModel.STATUS_NEUTRAL) {
                    progressDialogFragment.dismiss();
                    submitResultDialogFragment.setSuccess(status == SubmitViewModel.STATUS_OK);
                    submitResultDialogFragment.show(getSupportFragmentManager(), "SubmitActivity_SubmitResultDialog");
                }
            }
        });

        findViewById(R.id.btn_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

        TextView txtFirstName = findViewById(R.id.txtFirstName);
        TextView txtLastName = findViewById(R.id.txtLastName);
        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtProjectUrl = findViewById(R.id.txtProjectUrl);

        findViewById(R.id.button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submissionModel.setFirstName(txtFirstName.getText().toString().trim());
                        submissionModel.setLastName(txtLastName.getText().toString().trim());
                        submissionModel.setEmail(txtEmail.getText().toString().trim());
                        submissionModel.setProjectUrl(txtProjectUrl.getText().toString().trim());
                        boolean filledForm = submissionModel.getFirstName().length() > 0 && submissionModel.getLastName().length() > 0 && submissionModel.getEmail().length() > 0 && submissionModel.getProjectUrl().length() > 0;
                        if (!filledForm)
                            diologaOkFragment.show(getSupportFragmentManager(), "SubmitActivity_OkDialog");
                        else
                            confirmDialogFragment.show(getSupportFragmentManager(), "SubmitActivity_ConfirmDialog");
                    }
                });

    }

    @Override
    public void onConfirm(ConfirmDialogFragment confirmDialogFragment) {
        progressDialogFragment.show(getSupportFragmentManager(), "SubmitActivity_ProgressDialog");
        viewModel.submit(submissionModel);
    }

    @Override
    public void onDismiss(ConfirmDialogFragment confirmDialogFragment) {
    }
}