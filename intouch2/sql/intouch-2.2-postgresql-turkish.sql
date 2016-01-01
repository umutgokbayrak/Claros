--
-- PostgreSQL database dump
--

-- Started on 2007-09-10 01:31:17

SET client_encoding = 'LATIN5';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1688 (class 1262 OID 16403)
-- Name: intouch; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE intouch WITH TEMPLATE = template0 ENCODING = 'LATIN5';


\connect intouch

SET client_encoding = 'LATIN5';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1689 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- TOC entry 291 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- TOC entry 1296 (class 1259 OID 16404)
-- Dependencies: 5
-- Name: calendar_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE calendar_objects_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1297 (class 1259 OID 16406)
-- Dependencies: 1650 5
-- Name: calendar_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE calendar_objects (
    id bigint DEFAULT nextval('calendar_objects_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    record_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
    repeat_type integer NOT NULL,
    description text NOT NULL,
    reminder_days integer NOT NULL,
    color character varying(255) NOT NULL,
    "location" character varying(255) NOT NULL,
    reminder_method integer NOT NULL,
    reminded_before character varying(6),
    last_dismissed_at timestamp without time zone
);


--
-- TOC entry 1298 (class 1259 OID 16412)
-- Dependencies: 5
-- Name: contact_group_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE contact_group_objects_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1299 (class 1259 OID 16414)
-- Dependencies: 1651 5
-- Name: contact_group_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE contact_group_objects (
    id bigint DEFAULT nextval('contact_group_objects_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    group_id bigint NOT NULL,
    contact_id bigint NOT NULL
);


--
-- TOC entry 1300 (class 1259 OID 16417)
-- Dependencies: 5
-- Name: contact_groups_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE contact_groups_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1301 (class 1259 OID 16419)
-- Dependencies: 1652 5
-- Name: contact_groups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE contact_groups (
    id bigint DEFAULT nextval('contact_groups_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    short_name character varying(100) NOT NULL,
    description character varying(255) NOT NULL
);


--
-- TOC entry 1302 (class 1259 OID 16422)
-- Dependencies: 5
-- Name: contacts_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE contacts_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1303 (class 1259 OID 16424)
-- Dependencies: 1653 5
-- Name: contacts; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE contacts (
    id bigint DEFAULT nextval('contacts_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    first_name character varying(100) NOT NULL,
    middle_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL,
    title character varying(50) NOT NULL,
    sex character varying(10) NOT NULL,
    gsm_no_primary character varying(30) NOT NULL,
    gsm_no_alternate character varying(30) NOT NULL,
    email_primary character varying(255) NOT NULL,
    email_alternate character varying(255) NOT NULL,
    web_page character varying(255) NOT NULL,
    personal_note text NOT NULL,
    spouse_name character varying(255) NOT NULL,
    nick_name character varying(50) NOT NULL,
    home_address text NOT NULL,
    home_city character varying(255) NOT NULL,
    home_province character varying(255) NOT NULL,
    home_zip character varying(5) NOT NULL,
    home_country character varying(100) NOT NULL,
    home_phone character varying(30) NOT NULL,
    home_faks character varying(30) NOT NULL,
    work_company character varying(100) NOT NULL,
    work_job_title character varying(100) NOT NULL,
    work_department character varying(100) NOT NULL,
    work_office character varying(100) NOT NULL,
    work_profession character varying(100) NOT NULL,
    work_manager_name character varying(255) NOT NULL,
    work_assistant_name character varying(255) NOT NULL,
    work_address text NOT NULL,
    work_city character varying(255) NOT NULL,
    work_province character varying(255) NOT NULL,
    work_zip character varying(5) NOT NULL,
    work_country character varying(100) NOT NULL,
    work_phone character varying(30) NOT NULL,
    work_faks character varying(30) NOT NULL,
    birth_day character varying(2) NOT NULL,
    birth_month character varying(2) NOT NULL,
    anniversary_day character varying(2) NOT NULL,
    anniversary_month character varying(2) NOT NULL
);


--
-- TOC entry 1304 (class 1259 OID 16430)
-- Dependencies: 5
-- Name: folder_db_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE folder_db_objects_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1305 (class 1259 OID 16432)
-- Dependencies: 1654 5
-- Name: folder_db_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE folder_db_objects (
    id bigint DEFAULT nextval('folder_db_objects_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    parent_id bigint NOT NULL,
    folder_name character varying(100) NOT NULL,
    folder_type integer NOT NULL
);


--
-- TOC entry 1306 (class 1259 OID 16435)
-- Dependencies: 5
-- Name: msg_db_objects_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE msg_db_objects_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1307 (class 1259 OID 16437)
-- Dependencies: 1655 5
-- Name: msg_db_objects; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE msg_db_objects (
    id bigint DEFAULT nextval('msg_db_objects_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    folder_id bigint NOT NULL,
    unique_id character varying(100) NOT NULL,
    unread smallint NOT NULL,
    msg_size bigint NOT NULL,
    email bytea NOT NULL
);


--
-- TOC entry 1308 (class 1259 OID 16443)
-- Dependencies: 5
-- Name: msg_rules_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE msg_rules_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1309 (class 1259 OID 16445)
-- Dependencies: 1656 5
-- Name: msg_rules; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE msg_rules (
    id bigint DEFAULT nextval('msg_rules_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    portion character varying(100) NOT NULL,
    rule_condition character varying(30) NOT NULL,
    keyword character varying(255) NOT NULL,
    rule_action character varying(30) NOT NULL,
    destination character varying(100) NOT NULL
);


--
-- TOC entry 1310 (class 1259 OID 16448)
-- Dependencies: 5
-- Name: notes_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE notes_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1311 (class 1259 OID 16450)
-- Dependencies: 1657 5
-- Name: notes; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE notes (
    id bigint DEFAULT nextval('notes_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    folder_id bigint NOT NULL,
    note_content text NOT NULL,
    pos_left integer NOT NULL,
    pos_top integer NOT NULL,
    pos_width integer NOT NULL,
    pos_height integer NOT NULL,
    note_color character varying(20) NOT NULL,
    note_border_color character varying(20) NOT NULL,
    note_date timestamp without time zone NOT NULL,
    note_bar_color character varying(20)
);


--
-- TOC entry 1312 (class 1259 OID 16456)
-- Dependencies: 5
-- Name: notes_folders_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE notes_folders_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1313 (class 1259 OID 16458)
-- Dependencies: 1658 5
-- Name: notes_folders; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE notes_folders (
    id bigint DEFAULT nextval('notes_folders_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    folder_name character varying(255) NOT NULL
);


--
-- TOC entry 1314 (class 1259 OID 16461)
-- Dependencies: 5
-- Name: preferences_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE preferences_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1315 (class 1259 OID 16463)
-- Dependencies: 5
-- Name: queue_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE queue_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1316 (class 1259 OID 16465)
-- Dependencies: 1659 5
-- Name: queue; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE queue (
    id bigint DEFAULT nextval('queue_seq'::regclass) NOT NULL,
    msg_from character varying(255) NOT NULL,
    msg_to character varying(255) NOT NULL,
    msg_time timestamp without time zone NOT NULL,
    msg_date text NOT NULL,
    msg_direction character varying(3) NOT NULL,
    delivered integer NOT NULL
);


--
-- TOC entry 1318 (class 1259 OID 16496)
-- Dependencies: 5
-- Name: tasks_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE tasks_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1319 (class 1259 OID 16498)
-- Dependencies: 1661 5
-- Name: tasks; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE tasks (
    id bigint DEFAULT nextval('tasks_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    checked character varying(6),
    record_date timestamp without time zone,
    description text NOT NULL,
    priority integer,
    color character varying(255)
);


--
-- TOC entry 1317 (class 1259 OID 16471)
-- Dependencies: 1660 5
-- Name: user_preferences; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_preferences (
    id bigint DEFAULT nextval('preferences_seq'::regclass) NOT NULL,
    username character varying(255) NOT NULL,
    keyword character varying(255) NOT NULL,
    pref_value character varying(255) NOT NULL
);


--
-- TOC entry 1663 (class 2606 OID 16475)
-- Dependencies: 1297 1297
-- Name: CALENDAR_OBJECTS_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY calendar_objects
    ADD CONSTRAINT "CALENDAR_OBJECTS_pkey" PRIMARY KEY (id);


--
-- TOC entry 1669 (class 2606 OID 16477)
-- Dependencies: 1303 1303
-- Name: CONTACTS_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contacts
    ADD CONSTRAINT "CONTACTS_pkey" PRIMARY KEY (id);


--
-- TOC entry 1667 (class 2606 OID 16479)
-- Dependencies: 1301 1301
-- Name: CONTACT_GROUPS_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_groups
    ADD CONSTRAINT "CONTACT_GROUPS_pkey" PRIMARY KEY (id);


--
-- TOC entry 1665 (class 2606 OID 16481)
-- Dependencies: 1299 1299
-- Name: CONTACT_GROUP_OBJECTS_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY contact_group_objects
    ADD CONSTRAINT "CONTACT_GROUP_OBJECTS_pkey" PRIMARY KEY (id);


--
-- TOC entry 1671 (class 2606 OID 16483)
-- Dependencies: 1305 1305
-- Name: FOLDER_DB_OBJECTS_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY folder_db_objects
    ADD CONSTRAINT "FOLDER_DB_OBJECTS_pkey" PRIMARY KEY (id);


--
-- TOC entry 1673 (class 2606 OID 16485)
-- Dependencies: 1307 1307
-- Name: MSG_DB_OBJECTS_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY msg_db_objects
    ADD CONSTRAINT "MSG_DB_OBJECTS_pkey" PRIMARY KEY (id);


--
-- TOC entry 1675 (class 2606 OID 16487)
-- Dependencies: 1309 1309
-- Name: MSG_RULES_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY msg_rules
    ADD CONSTRAINT "MSG_RULES_pkey" PRIMARY KEY (id);


--
-- TOC entry 1679 (class 2606 OID 16489)
-- Dependencies: 1313 1313
-- Name: NOTES_FOLDERS_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY notes_folders
    ADD CONSTRAINT "NOTES_FOLDERS_pkey" PRIMARY KEY (id);


--
-- TOC entry 1677 (class 2606 OID 16491)
-- Dependencies: 1311 1311
-- Name: NOTES_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY notes
    ADD CONSTRAINT "NOTES_pkey" PRIMARY KEY (id);


--
-- TOC entry 1683 (class 2606 OID 16493)
-- Dependencies: 1317 1317
-- Name: PREFERENCES_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_preferences
    ADD CONSTRAINT "PREFERENCES_pkey" PRIMARY KEY (id);


--
-- TOC entry 1681 (class 2606 OID 16495)
-- Dependencies: 1316 1316
-- Name: QUEUE_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY queue
    ADD CONSTRAINT "QUEUE_pkey" PRIMARY KEY (id);


--
-- TOC entry 1685 (class 2606 OID 16505)
-- Dependencies: 1319 1319
-- Name: pk_tasks; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY tasks
    ADD CONSTRAINT pk_tasks PRIMARY KEY (id);


--
-- TOC entry 1690 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2007-09-10 01:31:18

--
-- PostgreSQL database dump complete
--

