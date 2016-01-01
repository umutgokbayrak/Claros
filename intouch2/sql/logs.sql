CREATE TABLE logs (
    message text,
    tarih character varying(255) DEFAULT ''::character varying,
    counter character varying(255) DEFAULT ''::character varying,
    place character varying(4000) DEFAULT ''::character varying
);
