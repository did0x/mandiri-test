package co.saputra.mandiritest.arch

import android.os.Bundle
import co.saputra.mandiritest.base.BaseActivity
import co.saputra.mandiritest.R

class MainActivity : BaseActivity() {

    override fun setView(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        return
    }
}