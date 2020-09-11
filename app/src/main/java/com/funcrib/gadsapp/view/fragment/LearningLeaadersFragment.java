package com.funcrib.gadsapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.funcrib.gadsapp.R;
import com.funcrib.gadsapp.adapter.LearningLeadersRecyclerViewAdapterAdapter;
import com.funcrib.gadsapp.model.LearningLeaderModel;
import com.funcrib.gadsapp.viewmodel.LearningLeadersViewModel;

import java.util.List;

public class LearningLeaadersFragment extends Fragment {

    private LearningLeadersViewModel viewModel;
    private DialogFragment errorDialogFragment;

    public LearningLeaadersFragment() {
        // Required empty public constructor
    }

    public static LearningLeaadersFragment newInstance() {
        LearningLeaadersFragment fragment = new LearningLeaadersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LearningLeadersViewModel.class);
        errorDialogFragment = DiologaOkFragment.newInstance(getString(R.string.network_error));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learning_leaders, container, false);
        final View emptyView = view.findViewById(R.id.emptyView);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshList();
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final LearningLeadersRecyclerViewAdapterAdapter adapter = new LearningLeadersRecyclerViewAdapterAdapter(getContext());
        recyclerView.setAdapter(adapter);
        viewModel.getLearningLeaders()
                .observe(this, new Observer<List<LearningLeaderModel>>() {
                    @Override
                    public void onChanged(List<LearningLeaderModel> learningLeaderModels) {
                        adapter.setItems(learningLeaderModels);
                        swipeRefreshLayout.setRefreshing(false);
                        if (learningLeaderModels.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                    }
                });
        viewModel.getError()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean error) {
                        if (error) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (adapter.getItemCount() > 0) {
                                Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                emptyView.setVisibility(View.VISIBLE);
                                errorDialogFragment.show(getFragmentManager(), "LearningLeadersFragment_OkDialog");
                            }
                        }
                    }
                });

        return view;
    }
}