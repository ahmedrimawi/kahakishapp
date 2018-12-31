package kahakish.shopping.com.kahakish.Behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View

class BottomNavigationBehavior (con: Context, attrs: AttributeSet):CoordinatorLayout.Behavior<View>(con, attrs) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

        child.translationY = Math.max(0f, Math.min(child.height.toFloat(), child.translationY+dyConsumed))
    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        if(dependency is Snackbar.SnackbarLayout){
            updateSnacker(child,dependency)

        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    private fun updateSnacker(child: View?, dependency: Snackbar.SnackbarLayout) {

        if(dependency.layoutParams is CoordinatorLayout.LayoutParams){
            val params =dependency.layoutParams as CoordinatorLayout.LayoutParams

            params.anchorId = child!!.id
            params.anchorGravity = Gravity.TOP
            params.gravity = Gravity.TOP
            dependency.layoutParams = params

        }

    }
}