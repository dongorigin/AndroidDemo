package cn.dong.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.dong.demo.R;

public class ShareActivity extends Activity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        tv = (TextView) findViewById(R.id.text);

        Button button = (Button) findViewById(R.id.bn_share);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nativeShare();
                // filterShare();
            }
        });
    }

    private void nativeShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
        intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "请选择"));
    }

    private void filterShare() {
        String contentDetails = "";
        String contentBrief = "";
        String shareUrl = "";
        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(it, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("text/plain");
                ActivityInfo activityInfo = info.activityInfo;

                // judgments : activityInfo.packageName, activityInfo.name, etc.
                if (activityInfo.packageName.contains("bluetooth")
                        || activityInfo.name.contains("bluetooth")) {
                    continue;
                }
                if (activityInfo.packageName.contains("gm") || activityInfo.name.contains("mail")) {
                    targeted.putExtra(Intent.EXTRA_TEXT, contentDetails);
                } else if (activityInfo.packageName.contains("zxing")) {
                    targeted.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } else {
                    targeted.putExtra(Intent.EXTRA_TEXT, contentBrief);
                }
                targeted.setPackage(activityInfo.packageName);
                targetedShareIntents.add(targeted);
            }

            Intent chooserIntent =
                    Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
            if (chooserIntent == null) {
                return;
            }

            // A Parcelable[] of Intent or LabeledIntent objects as set with
            // putExtra(String, Parcelable[]) of additional activities to place
            // a the front of the list of choices, when shown to the user with a
            // ACTION_CHOOSER.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    targetedShareIntents.toArray(new Parcelable[] {}));

            try {
                startActivity(chooserIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Can't find share component to share", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
