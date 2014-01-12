package mcdelta.essentialalloys.config;

import mcdelta.core.config.ConfigWrapper;
import mcdelta.core.config.IConfig;

public class EAConfig extends IConfig
{
     @Override
     protected void initCommon (final ConfigWrapper config)
     {
          config.getConfiguration().addCustomCategoryComment(EASettings.CATEGORY_GENERAL, EASettings.COMMENT_GENERAL);
          EASettings.BLASTFURNACE_TIME = config.get(EASettings.CATEGORY_GENERAL, EASettings.BLASTFURNACE_TIME_KEY, 200);
     }
}