package com.afzaln.pgshoutbox.util;

import java.lang.reflect.Field;

import com.afzaln.pgshoutbox.R;
import timber.log.Timber;

/**
 * Created by afzal on 2016-06-09.
 */
public class ResourceUtils {
    public static int getDrawableIdByName(String iconName) {
        try {
            Class res = R.drawable.class;
            Field field = res.getField(iconName);
            int drawableId = field.getInt(null);
            return drawableId;

        }
        catch (Exception e) {
            Timber.e(e, "Failure to get drawable id. %s", iconName);
        }

        return 0;
    }
}
