package com.fantasy.excape.fantasy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by song on 2016/1/9.
 */
public class ExplainActivity extends Activity {
    private String[] explainTitle;
    private String[] explainTmp;
    private String[][] explainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain_layout);

        Resources res = getResources();
        explainTitle = res.getStringArray(R.array.explain_question);
        explainTmp = res.getStringArray(R.array.explain_answer);
        explainText = new String[explainTmp.length][1];

        for (int i=0; i<explainTmp.length; i++) {
            explainText[i][0] = explainTmp[i];
        }

        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

            @Override
            public int getGroupCount() {
                return explainTitle.length;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return explainText[groupPosition].length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return explainTitle[groupPosition];
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return explainText[groupPosition][childPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) ExplainActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.list_group, null);
                }

                TextView lblListHeader = (TextView) convertView
                        .findViewById(R.id.lblListHeader);
                lblListHeader.setTypeface(null, Typeface.BOLD);
                lblListHeader.setText(explainTitle[groupPosition]);

                return convertView;
            }

            @Override
            public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) ExplainActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.explain_list_item, null);
                }
                TextView item = (TextView) convertView.findViewById(R.id.explain_item);
                item.setText(explainText[groupPosition][childPosition]);

                final int position = groupPosition;
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        switch (position) {
                            case 3:
                                intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("message/rfc822");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sooon1g1@gmail.com"});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Question for MemoryFragments");
                                try {
                                    startActivity(Intent.createChooser(intent, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(ExplainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 4:

                                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/song1112/Excape-MemoryFragments"));
                                startActivity(intent);
                                break;
                        }

                    }
                });

                return convertView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }
        };
        ExpandableListView expandListView = (ExpandableListView) this.findViewById(R.id.explain);
        expandListView.setAdapter(adapter);


    }


}
