package com.funcrib.gadsapp.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DiologaOkFragment extends DialogFragment {
    public static final String ARG_MESSAGE = "message";

    public DiologaOkFragment() {
        Bundle defaultArgs = new Bundle();
        defaultArgs.putString(ARG_MESSAGE, "");
        setArguments(defaultArgs);
    }

    public static DiologaOkFragment newInstance(String message) {
        DiologaOkFragment dialog = new DiologaOkFragment();
        dialog.getArguments().putString(ARG_MESSAGE, message);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setMessage(getArguments().getString(ARG_MESSAGE))
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

}
