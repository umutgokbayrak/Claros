
CREATE TABLE CALENDAR_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `RECORD_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `END_DATE` timestamp NULL default NULL,
  `REPEAT_TYPE` int(11) default '0',
  `DESCRIPTION` text collate latin5_bin,
  `REMINDER_DAYS` int(11) default '0',
  `COLOR` varchar(10) collate latin5_bin default NULL,
  `LOCATION` varchar(255) collate latin5_bin default NULL,
  `REMINDER_METHOD` int(11) default NULL,
  `REMINDED_BEFORE` varchar(6) collate latin5_bin default NULL,
  `LAST_DISMISSED_AT` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE CONTACT_GROUP_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `GROUP_ID` bigint(20) NOT NULL default '0',
  `CONTACT_ID` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE CONTACT_GROUPS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `SHORT_NAME` varchar(100) collate latin5_bin default '',
  `DESCRIPTION` varchar(255) collate latin5_bin default '',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE CONTACTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin default '',
  `FIRST_NAME` varchar(100) collate latin5_bin NOT NULL default '',
  `MIDDLE_NAME` varchar(100) collate latin5_bin default '',
  `LAST_NAME` varchar(100) collate latin5_bin NOT NULL default '',
  `TITLE` varchar(50) collate latin5_bin default '',
  `SEX` varchar(10) collate latin5_bin default '',
  `GSM_NO_PRIMARY` varchar(30) collate latin5_bin default '',
  `GSM_NO_ALTERNATE` varchar(30) collate latin5_bin default '',
  `EMAIL_PRIMARY` varchar(255) collate latin5_bin default '',
  `EMAIL_ALTERNATE` varchar(255) collate latin5_bin default '',
  `WEB_PAGE` varchar(255) collate latin5_bin default '',
  `PERSONAL_NOTE` text collate latin5_bin,
  `SPOUSE_NAME` varchar(255) collate latin5_bin default '',
  `NICK_NAME` varchar(50) collate latin5_bin default '',
  `HOME_ADDRESS` text collate latin5_bin,
  `HOME_CITY` varchar(255) collate latin5_bin default '',
  `HOME_PROVINCE` varchar(255) collate latin5_bin default '',
  `HOME_ZIP` varchar(5) collate latin5_bin default '',
  `HOME_COUNTRY` varchar(100) collate latin5_bin default '',
  `HOME_PHONE` varchar(30) collate latin5_bin default '',
  `HOME_FAKS` varchar(30) collate latin5_bin default '',
  `WORK_COMPANY` varchar(100) collate latin5_bin default '',
  `WORK_JOB_TITLE` varchar(100) collate latin5_bin default '',
  `WORK_DEPARTMENT` varchar(100) collate latin5_bin default '',
  `WORK_OFFICE` varchar(100) collate latin5_bin default '',
  `WORK_PROFESSION` varchar(100) collate latin5_bin default '',
  `WORK_MANAGER_NAME` varchar(255) collate latin5_bin default '',
  `WORK_ASSISTANT_NAME` varchar(255) collate latin5_bin default '',
  `WORK_ADDRESS` text collate latin5_bin,
  `WORK_CITY` varchar(255) collate latin5_bin default '',
  `WORK_PROVINCE` varchar(255) collate latin5_bin default '',
  `WORK_ZIP` varchar(5) collate latin5_bin default '',
  `WORK_COUNTRY` varchar(100) collate latin5_bin default '',
  `WORK_PHONE` varchar(30) collate latin5_bin default '',
  `WORK_FAKS` varchar(30) collate latin5_bin default '',
  `BIRTH_DAY` char(2) collate latin5_bin default NULL,
  `ANNIVERSARY_DAY` char(2) collate latin5_bin default NULL,
  `BIRTH_MONTH` char(2) collate latin5_bin default NULL,
  `ANNIVERSARY_MONTH` char(2) collate latin5_bin default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE FOLDER_DB_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin default NULL,
  `PARENT_ID` bigint(20) default '0',
  `FOLDER_NAME` varchar(100) collate latin5_bin NOT NULL default '',
  `FOLDER_TYPE` int(10) unsigned NOT NULL default '4',
  PRIMARY KEY  (`ID`),
  KEY `USERNAME` (`USERNAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE MSG_DB_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `FOLDER_ID` bigint(20) unsigned NOT NULL default '0',
  `UNIQUE_ID` varchar(100) collate latin5_bin NOT NULL default '',
  `UNREAD` tinyint(1) default '0',
  `MSG_SIZE` bigint(20) unsigned NOT NULL default '0',
  `EMAIL` longblob,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE MSG_RULES (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `PORTION` varchar(100) collate latin5_bin default NULL,
  `RULE_CONDITION` varchar(30) collate latin5_bin default NULL,
  `KEYWORD` varchar(255) collate latin5_bin default NULL,
  `RULE_ACTION` varchar(30) collate latin5_bin default NULL,
  `DESTINATION` varchar(100) collate latin5_bin default NULL,
  PRIMARY KEY  (`ID`),
  KEY `USERNAME` (`USERNAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE NOTES (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `FOLDER_ID` bigint(20) NOT NULL default '0',
  `NOTE_CONTENT` text collate latin5_bin,
  `POS_LEFT` int(11) default NULL,
  `POS_TOP` int(11) default NULL,
  `POS_WIDTH` int(11) default NULL,
  `POS_HEIGHT` int(11) default NULL,
  `NOTE_COLOR` varchar(20) collate latin5_bin default NULL,
  `NOTE_BAR_COLOR` varchar(20) collate latin5_bin default NULL,
  `NOTE_BORDER_COLOR` varchar(20) collate latin5_bin default NULL,
  `NOTE_DATE` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE NOTES_FOLDERS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `FOLDER_NAME` varchar(255) collate latin5_bin NOT NULL default '',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE QUEUE (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `MSG_FROM` varchar(255) collate latin5_bin NOT NULL default '',
  `MSG_TO` varchar(255) collate latin5_bin NOT NULL default '',
  `MSG_TIME` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `MSG_BODY` longtext collate latin5_bin NOT NULL,
  `MSG_DIRECTION` char(3) collate latin5_bin NOT NULL default '',
  `DELIVERED` int(11) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

CREATE TABLE TASKS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) default NULL,
  `CHECKED` varchar(6) default NULL,
  `RECORD_DATE` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `DESCRIPTION` text,
  `PRIORITY` int(11) default NULL,
  `COLOR` varchar(100) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin5;

CREATE TABLE USER_PREFERENCES (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) collate latin5_bin NOT NULL default '',
  `KEYWORD` varchar(255) collate latin5_bin NOT NULL default '',
  `PREF_VALUE` varchar(255) collate latin5_bin NOT NULL default '',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin5 COLLATE=latin5_bin;

