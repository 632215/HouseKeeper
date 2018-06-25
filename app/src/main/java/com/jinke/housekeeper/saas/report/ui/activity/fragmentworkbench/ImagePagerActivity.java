package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.jinke.housekeeper.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 */

public class ImagePagerActivity extends FragmentActivity {
    public static final String INTENT_IMGURLS = "imgUrl";
    public static final String INTENT_CURRENT_POSITION = "currentPosition";
    private Context mContext;
    public static final String INTENT_IMAGE_LIST_SIZE = "imageListSize";

    private ViewPager mViewPager;
    private TextView mNum;


    private List<String> imageUrlList;
    private int currentPosition;

    public static void startActivity(Context context, List<String> imageUrl, int currentPosition) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(INTENT_CURRENT_POSITION, currentPosition);
        intent.putStringArrayListExtra(INTENT_IMGURLS, (ArrayList<String>) imageUrl);
        intent.putExtra(INTENT_IMAGE_LIST_SIZE, imageUrl.size());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        initView();
        getIntentData();
        initAdapter();
    }

    private void getIntentData() {
        imageUrlList = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        currentPosition = getIntent().getIntExtra(INTENT_CURRENT_POSITION, 0);
    }

    private void initAdapter() {
        ImageAdapter adapter = new ImageAdapter(this);
        adapter.setListener(new ImageAdapter.OnShutDownListener() {
            @Override
            public void onShutDown() {
                finish();
            }
        });
        adapter.setImageUrlList(imageUrlList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String num = String.valueOf(position + 1);
                mNum.setText(num + "/" + imageUrlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(currentPosition);
        mNum.setText(currentPosition + 1 + "/" + imageUrlList.size());

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.activity_image_pager_viewpager);
        mNum = (TextView) findViewById(R.id.activity_image_pager_tx_num);
    }


    private static class ImageAdapter extends PagerAdapter {
        private List<String> imageUrlList = new ArrayList<>();
        private LayoutInflater mInflater;
        private Context mContext;
        private OnShutDownListener listener;

        private ImageAdapter(ImagePagerActivity context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
        }

        public void setListener(OnShutDownListener listener) {
            this.listener = listener;
        }

        private void setImageUrlList(List<String> imageUrlList) {
            this.imageUrlList = imageUrlList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (imageUrlList != null) {
                return imageUrlList.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mInflater.inflate(R.layout.item_pager_image, container, false);
            if (view != null) {
                PhotoView photoView = (PhotoView) view.findViewById(R.id.item_photo_view);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onShutDown();
                    }
                });
                photoView.enable();
                if (imageUrlList.get(position).toString() != null && !imageUrlList.get(position).toString().equals("")) {
                    File file = new File(imageUrlList.get(position));
                    if (file.exists()) {
                        Picasso.with(mContext)
                                .load(file)
                                .error(R.drawable.simal).placeholder(R.drawable.simal)
                                .into(photoView);
                    } else {
                        Picasso.with(mContext)
                                .load(imageUrlList.get(position))
                                .error(R.drawable.simal).placeholder(R.drawable.simal)
                                .into(photoView);
                    }
                }
                container.addView(view);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        interface OnShutDownListener {
            void onShutDown();
        }
    }

}
