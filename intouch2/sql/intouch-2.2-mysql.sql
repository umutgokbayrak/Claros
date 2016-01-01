
CREATE TABLE CALENDAR_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `RECORD_DATE` timestamp NOT NULL,
  `END_DATE` timestamp NULL default NULL,
  `REPEAT_TYPE` int(11) default '0',
  `DESCRIPTION` text,
  `REMINDER_DAYS` int(11) default '0',
  `COLOR` varchar(10) default NULL,
  `LOCATION` varchar(255) default NULL,
  `REMINDER_METHOD` int(11) default NULL,
  `REMINDED_BEFORE` varchar(6) default NULL,
  `LAST_DISMISSED_AT` timestamp NULL default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE CONTACT_GROUP_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `GROUP_ID` bigint(20) NOT NULL default '0',
  `CONTACT_ID` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE CONTACT_GROUPS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `SHORT_NAME` varchar(100) default '',
  `DESCRIPTION` varchar(255) default '',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE CONTACTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) default '',
  `FIRST_NAME` varchar(100) NOT NULL default '',
  `MIDDLE_NAME` varchar(100) default '',
  `LAST_NAME` varchar(100) NOT NULL default '',
  `TITLE` varchar(50) default '',
  `SEX` varchar(10) default '',
  `GSM_NO_PRIMARY` varchar(30) default '',
  `GSM_NO_ALTERNATE` varchar(30) default '',
  `EMAIL_PRIMARY` varchar(255) default '',
  `EMAIL_ALTERNATE` varchar(255) default '',
  `WEB_PAGE` varchar(255) default '',
  `PERSONAL_NOTE` text,
  `SPOUSE_NAME` varchar(255) default '',
  `NICK_NAME` varchar(50) default '',
  `HOME_ADDRESS` text,
  `HOME_CITY` varchar(255) default '',
  `HOME_PROVINCE` varchar(255) default '',
  `HOME_ZIP` varchar(5) default '',
  `HOME_COUNTRY` varchar(100) default '',
  `HOME_PHONE` varchar(30) default '',
  `HOME_FAKS` varchar(30) default '',
  `WORK_COMPANY` varchar(100) default '',
  `WORK_JOB_TITLE` varchar(100) default '',
  `WORK_DEPARTMENT` varchar(100) default '',
  `WORK_OFFICE` varchar(100) default '',
  `WORK_PROFESSION` varchar(100) default '',
  `WORK_MANAGER_NAME` varchar(255) default '',
  `WORK_ASSISTANT_NAME` varchar(255) default '',
  `WORK_ADDRESS` text,
  `WORK_CITY` varchar(255) default '',
  `WORK_PROVINCE` varchar(255) default '',
  `WORK_ZIP` varchar(5) default '',
  `WORK_COUNTRY` varchar(100) default '',
  `WORK_PHONE` varchar(30) default '',
  `WORK_FAKS` varchar(30) default '',
  `BIRTH_DAY` char(2) default NULL,
  `ANNIVERSARY_DAY` char(2) default NULL,
  `BIRTH_MONTH` char(2) default NULL,
  `ANNIVERSARY_MONTH` char(2) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE FOLDER_DB_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) default NULL,
  `PARENT_ID` bigint(20) default '0',
  `FOLDER_NAME` varchar(100) NOT NULL default '',
  `FOLDER_TYPE` int(10) unsigned NOT NULL default '4',
  PRIMARY KEY  (`ID`),
  KEY `USERNAME` (`USERNAME`)
) ENGINE=MyISAM;

CREATE TABLE MSG_DB_OBJECTS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `FOLDER_ID` bigint(20) unsigned NOT NULL default '0',
  `UNIQUE_ID` varchar(100) NOT NULL default '',
  `UNREAD` tinyint(1) default '0',
  `MSG_SIZE` bigint(20) unsigned NOT NULL default '0',
  `EMAIL` longblob,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE MSG_RULES (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `PORTION` varchar(100) default NULL,
  `RULE_CONDITION` varchar(30) default NULL,
  `KEYWORD` varchar(255) default NULL,
  `RULE_ACTION` varchar(30) default NULL,
  `DESTINATION` varchar(100) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `USERNAME` (`USERNAME`)
) ENGINE=MyISAM;

CREATE TABLE NOTES (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `FOLDER_ID` bigint(20) NOT NULL default '0',
  `NOTE_CONTENT` text,
  `POS_LEFT` int(11) default NULL,
  `POS_TOP` int(11) default NULL,
  `POS_WIDTH` int(11) default NULL,
  `POS_HEIGHT` int(11) default NULL,
  `NOTE_COLOR` varchar(20) default NULL,
  `NOTE_BAR_COLOR` varchar(20) default NULL,
  `NOTE_BORDER_COLOR` varchar(20) default NULL,
  `NOTE_DATE` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE NOTES_FOLDERS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `FOLDER_NAME` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE QUEUE (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `MSG_FROM` varchar(255) NOT NULL default '',
  `MSG_TO` varchar(255) NOT NULL default '',
  `MSG_TIME` timestamp NOT NULL,
  `MSG_BODY` longtext NOT NULL,
  `MSG_DIRECTION` char(3) NOT NULL default '',
  `DELIVERED` int(11) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE TASKS (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) default NULL,
  `CHECKED` varchar(6) default NULL,
  `RECORD_DATE` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  `DESCRIPTION` text,
  `PRIORITY` int(11) default NULL,
  `COLOR` varchar(100) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

CREATE TABLE USER_PREFERENCES (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `USERNAME` varchar(255) NOT NULL default '',
  `KEYWORD` varchar(255) NOT NULL default '',
  `PREF_VALUE` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM;

