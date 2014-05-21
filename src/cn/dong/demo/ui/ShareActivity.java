package cn.dong.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.dong.demo.R;
import cn.dong.demo.widget.SharePage;

public class ShareActivity extends Activity {
	private static final String EMAIL_FORMAT = "<html><body>"
			+ "%s<br/>"
			+ "<a href='http://sf.lianxi.com/m/webClientDownLoad.html' target='_blank'>Link</a>"
			+ "<br/>"
			+ "<img src='%s' width='100' height='100' />"
			+ "<br/>"
			+ "<img src='data:image/jpg;base64,/9j/4AAQSkZJRgABAQEBLAEsAAD/4QLQRXhpZgAASUkqAEoCAAAsAQAAAQAAACwBAAABAAAAAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AAAAAAEBCDwAAAAAAQEIPAAAAAABAQg8AGgCaggUAAQAAAKAAAACdggUAAQAAAKgAAAAiiAMAAQAAAAAAAAAniAMAAgAAAAAAAAAAkAcABAAAADAyMTACkQUAAQAAALAAAAABkgUAAQAAALgAAAACkgUAAQAAAMAAAAADkgUAAQAAAMgAAAAEkgUAAQAAANAAAAAFkgUAAQAAANgAAAAGkgUAAQAAAOAAAAAHkgMAAQAAAAAAAAAIkgMAAQAAAAAAAAAJkgMAAQAAAAAAAAAKkgUAAQAAAOgAAAABoAMAAQAAAAAAAAACoAMAAQAAAFUAAAADoAMAAQAAAFUAAAAOogUAAQAAAPAAAAAPogUAAQAAAPgAAAAQogMAAQAAAAAAAAAVogUAAQAAAAABAAAXogMAAQAAAAAAAAAAowMAAQAAAAAAAAABowMAAQAAAAAAAAAAAAAACAEAAAoAEgEDAAEAAAABAAAAGgEFAAEAAAAIAAAAGwEFAAEAAAAQAAAAKAEDAAEAAAACAAAAPgEFAAIAAAAYAAAAPwEFAAYAAAAoAAAAEQIFAAMAAABYAAAAEwIDAAEAAAAAAAAAFAIFAAYAAABwAAAAaYcEAAEAAAAIAQAAAAAAAP/sABFEdWNreQABAAQAAAA8AAD/4QNtaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjMtYzAxMSA2Ni4xNDU2NjEsIDIwMTIvMDIvMDYtMTQ6NTY6MjcgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdFJlZj0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlUmVmIyIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bXBNTTpPcmlnaW5hbERvY3VtZW50SUQ9InhtcC5kaWQ6NkFDODdFMEUyMDVFRTIxMTgyOTNCNTk5RjAwQkIwRTciIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NjIxRkY1RDQ4QUQ4MTFFMjgwNjI4NEQ4QzgxNTEwQTUiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NjIxRkY1RDM4QUQ4MTFFMjgwNjI4NEQ4QzgxNTEwQTUiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6Mjk1NkVGQjgyMDhBRTIxMTg2MDlGODI3Nzk4REI2NkUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NkFDODdFMEUyMDVFRTIxMTgyOTNCNTk5RjAwQkIwRTciLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7/2wBDAAUEBAQEAwUEBAQGBQUGCA0ICAcHCBALDAkNExAUExIQEhIUFx0ZFBYcFhISGiMaHB4fISEhFBkkJyQgJh0gISD/2wBDAQUGBggHCA8ICA8gFRIVICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICD/wAARCABVAFUDAREAAhEBAxEB/8QA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' alt='Base64 encoded image'/>"
			+ "<br/>" + "<font color=#808080 size=0.3>点击或扫描图片下载顺丰速运</font>" + "</html></body>";

	SharePage sharePage;
	private TextView tv;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		tv = (TextView) findViewById(R.id.text);
		sharePage = new SharePage(this);

		content = String.format(EMAIL_FORMAT, "分享的内容", "http://www.baidu.com/img/bdlogo.gif");

		Button button = (Button) findViewById(R.id.bn_share);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 sharePage.show();
				// email();
//				nativeShare();
//				filterShare();
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

			Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0),
					"Select app to share");
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

	private void email() {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		intent.setType("message/rfc822");
		intent.setData(Uri.parse("mailto:"));
		intent.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		// tv.setText(Html.fromHtml(content, null, null));
		startActivity(intent);
	}

}
