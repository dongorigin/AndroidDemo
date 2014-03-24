package cn.dong.demo.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import cn.dong.demo.DongApplication;

public class CameraUtil {
	private Activity context;
	private final String FILE_SAVEPATH;
	private String protraitPath;
	private Uri origUri;
	private Uri cropUri;
	private File protraitFile;
	// private final static int CROP = 200;
	private int CROPWIDTH = 200;
	private int CROPHEIGHT = 200;
	private int zoomX = 1;
	private int zoomY = 1;

	public CameraUtil(Activity context) {
		this.context = context;
		DongApplication application = (DongApplication) context.getApplication();
		FILE_SAVEPATH = application.IMG_DIR;
	}

	public Uri getOrigUri() {
		return this.origUri;
	}

	public String getProtraitPath() {
		return this.protraitPath;
	}

	/**
	 * 输出的尺寸
	 * setCrop(这里用一句话描述这个方法的作用)
	 * 
	 * @Title: setCrop
	 * @Description: TODO
	 * @param @param CROPWIDTH
	 * @param @param CROPHEIGHT 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void setCrop(int CROPWIDTH, int CROPHEIGHT) {
		this.CROPHEIGHT = CROPHEIGHT;
		this.CROPWIDTH = CROPWIDTH;
	}

	/**
	 * 
	 * setZoom(这里用一句话描述这个方法的作用)
	 * 
	 * @Title: setZoom
	 * @Description: TODO
	 * @param @param zoomX
	 * @param @param zoomY 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void setZoom(int zoomX, int zoomY) {
		this.zoomX = zoomX;
		this.zoomY = zoomY;
	}

	/**
	 * 相机拍照
	 * 
	 * @param output
	 */
	public void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
		intent.putExtra("return-data", true);
		context.startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	// 拍照保存的绝对路径
	private Uri getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			T.shortT(context, "无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 照片命名
		String cropFileName = "tixa_enter_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		this.origUri = this.cropUri;
		return this.cropUri;
	}

	/**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	public void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		context.startActivityForResult(Intent.createChooser(intent, "选择图片"),
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	/**
	 * 拍照后裁剪
	 * 
	 * @param data
	 *            原始图片
	 * @param output
	 *            裁剪后图片
	 */
	public void startActionCrop(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", this.getUploadTempFile(data));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", zoomX);// 裁剪框比例
		intent.putExtra("aspectY", zoomY);
		intent.putExtra("outputX", CROPWIDTH);// 输出图片大小
		intent.putExtra("outputY", CROPHEIGHT);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		context.startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	// 裁剪头像的绝对路径
	private Uri getUploadTempFile(Uri uri) {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			T.shortT(context, "无法保存上传的头像，请检查SD卡是否挂载");
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (StrUtil.isEmpty(thePath)) {
			thePath = ImageUtils.getAbsoluteImagePath(context, uri);
		}
		String ext = FileUtil.getSuffix(thePath);
		ext = StrUtil.isEmpty(ext) ? ".jpg" : ext;
		// 照片命名
		String cropFileName = "tixa_enter_" + timeStamp + ext;
		// 裁剪头像的绝对路径
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);

		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}

}
