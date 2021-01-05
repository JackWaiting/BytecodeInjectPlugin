package com.hencoder.gradleplugin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sf.module.liblog.BoxLogger
import com.sf.module.liblog.strategy.LogConfigs

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Thread.sleep(1000)
    BoxLogger.getInstance(this)
    BoxLogger.wInfo("[Boot]执行onCreate:", "测试", "测试")
    test()
  }

  private fun test() {
    Thread.sleep(1000)
  }

  override fun onResume() {
    super.onResume()
    BoxLogger.wInfo("[Boot]执行onResume:", "测试", "测试")
  }
}