package com.fantasy.excape.fantasy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
/**
 * Created by song on 2016/1/9.
 */
public class ExplainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain_layout);


        ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

            //內容
            private String[] explainTitle = new String[]{};
            private String[][] explainText = new String[][]{};

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
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) ExplainActivity.this
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.explain_list_item, null);
                }
                TextView item = (TextView)convertView.findViewById(R.id.explain_item);
                item.setText(explainText[groupPosition][childPosition]);

                if(groupPosition==14) {
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("text/*");
                            intent.putExtra(Intent.EXTRA_TEXT,"我想跟你分享一個很有趣的任務管理App，但是還沒上架 :(");
                            startActivity(intent);
                        }
                    });
                }

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
