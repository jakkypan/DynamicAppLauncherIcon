package giant.dynamicapplaunchericon;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ComponentName mDefault;
    private ComponentName mDouble11;
    private ComponentName mDouble12;
    private ComponentName mDouble13;
    private PackageManager mPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefault = getComponentName();
        mDouble11 = new ComponentName(
                getBaseContext(),
                "giant.dynamicapplaunchericon.MainActivityAlias1");
        mDouble12 = new ComponentName(
                getBaseContext(),
                "giant.dynamicapplaunchericon.MainActivityAlias2");
        mDouble13 = new ComponentName(
                getBaseContext(),
                "giant.dynamicapplaunchericon.MainActivityAlias3");
        mPm = getApplicationContext().getPackageManager();
    }

    public void myDefault(View view) {
        enableComponent(mDefault);
        disableComponent(mDouble12);
        disableComponent(mDouble13);
        disableComponent(mDouble11);
        immedate();
    }

    public void changeIcon11(View view) {
        disableComponent(mDefault);
        disableComponent(mDouble12);
        disableComponent(mDouble13);
        enableComponent(mDouble11);
        immedate();
    }

    public void changeIcon12(View view) {
        disableComponent(mDefault);
        disableComponent(mDouble11);
        disableComponent(mDouble13);
        enableComponent(mDouble12);
        immedate();
    }

    public void changeIcon13(View view) {
        disableComponent(mDefault);
        disableComponent(mDouble11);
        disableComponent(mDouble12);
        enableComponent(mDouble13);
        immedate();
    }

    private void enableComponent(ComponentName componentName) {
        mPm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableComponent(ComponentName componentName) {
        mPm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void immedate() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = mPm.queryIntentActivities(intent, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }
}
