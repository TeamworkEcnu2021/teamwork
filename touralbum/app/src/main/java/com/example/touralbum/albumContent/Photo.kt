package com.example.touralbum.albumContent

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class Photo(val image : Bitmap) {
}

/**
 * 将Bitmap转换成字符串保存至SharedPreferences
 *
 * 注意:
 * 在压缩图片至输出流时,不要选择CompressFormat.JPEG而该是PNG,否则会造成图片有黑色背景.
 * 详见参考资料二
 */
/*private fun saveBitmapToSharedPreferences() {
    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher)
    //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(CompressFormat.PNG, 80, byteArrayOutputStream)
    //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    val imageString: String = String(Base64.encodeToString(byteArray, Base64.DEFAULT))
    //第三步:将String保持至SharedPreferences
    val sharedPreferences = getSharedPreferences("testSP", MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("image", imageString)
    editor.apply()
}*/


/**
 * 从SharedPreferences中取出Bitmap
 */
/*private fun getBitmapFromSharedPreferences() {
    val sharedPreferences = getSharedPreferences("testSP", MODE_PRIVATE)
    //第一步:取出字符串形式的Bitmap
    val imageString = sharedPreferences.getString("image", "")
    //第二步:利用Base64将字符串转换为ByteArrayInputStream
    val byteArray: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
    val byteArrayInputStream = ByteArrayInputStream(byteArray)
    //第三步:利用ByteArrayInputStream生成Bitmap
    val bitmap = BitmapFactory.decodeStream(byteArrayInputStream)
    mImageView.setImageBitmap(bitmap)
}*/

/*private fun drawableToBitmap(drawable: Drawable): Bitmap {
    val w = drawable.intrinsicWidth
    val h = drawable.intrinsicHeight
    // 取 drawable 的颜色格式
    val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
    val bitmap = Bitmap.createBitmap(w, h, config)
    //建立对应 bitmap 的画布
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, w, h)
    // 把 drawable 内容画到画布中
    drawable.draw(canvas)
    return bitmap
}*/