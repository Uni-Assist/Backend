--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6 (Ubuntu 16.6-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.6 (Ubuntu 16.6-0ubuntu0.24.04.1)

-- Started on 2025-01-19 16:12:32 +07

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE "UniAssistDB";
--
-- TOC entry 3507 (class 1262 OID 16660)
-- Name: UniAssistDB; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE "UniAssistDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'ru_RU.UTF-8';


\connect "UniAssistDB"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16661)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 3508 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 911 (class 1247 OID 16873)
-- Name: ResponseType; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."ResponseType" AS ENUM (
    'TEXT',
    'FILE'
);


--
-- TOC entry 908 (class 1247 OID 16856)
-- Name: SubjectType; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."SubjectType" AS ENUM (
    'LECTURE',
    'SEMINAR'
);


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16713)
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


--
-- TOC entry 218 (class 1259 OID 16718)
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


--
-- TOC entry 219 (class 1259 OID 16721)
-- Name: groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.groups (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(15) NOT NULL
);


--
-- TOC entry 220 (class 1259 OID 16725)
-- Name: responses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.responses (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    student_id uuid NOT NULL,
    task_id uuid NOT NULL,
    body text,
    mark integer NOT NULL,
    response_type public."ResponseType" NOT NULL,
    CONSTRAINT responses_mark_check CHECK (((mark >= 0) AND (mark <= 100)))
);


--
-- TOC entry 221 (class 1259 OID 16732)
-- Name: students; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.students (
    student_id uuid DEFAULT gen_random_uuid() NOT NULL,
    fathername character varying(15),
    surname character varying(15),
    name character varying(15) NOT NULL,
    login character varying(15) NOT NULL,
    password character varying(15) NOT NULL,
    phone integer NOT NULL,
    email character varying(30) NOT NULL,
    birthdate date NOT NULL,
    group_id uuid NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 16709)
-- Name: subjects; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.subjects (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    subject_name character varying(40) NOT NULL,
    teacher_id uuid NOT NULL,
    group_id uuid NOT NULL,
    start_time time without time zone NOT NULL,
    end_time time without time zone NOT NULL,
    classroom character varying(10) NOT NULL,
    date date NOT NULL,
    type public."SubjectType" NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 16736)
-- Name: tasks; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tasks (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    header character varying(40) NOT NULL,
    body text NOT NULL,
    due_time timestamp without time zone NOT NULL,
    teacher_id uuid,
    class_id uuid
);


--
-- TOC entry 223 (class 1259 OID 16742)
-- Name: teachers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.teachers (
    teacher_id uuid DEFAULT gen_random_uuid() NOT NULL,
    fathername character varying(15),
    surrname character varying(15),
    name character varying(15) NOT NULL,
    login character varying(15) NOT NULL,
    password character varying(15) NOT NULL,
    phone integer NOT NULL,
    email character varying(30) NOT NULL,
    birthdate date NOT NULL,
    department character varying(30) NOT NULL,
    job_title character varying(40) NOT NULL
);


--
-- TOC entry 3495 (class 0 OID 16713)
-- Dependencies: 217
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3496 (class 0 OID 16718)
-- Dependencies: 218
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.databasechangeloglock VALUES (1, false, NULL, NULL);


--
-- TOC entry 3497 (class 0 OID 16721)
-- Dependencies: 219
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.groups VALUES ('9f7706b2-ce24-4042-af4a-4302a23d521f', 'IV-221');
INSERT INTO public.groups VALUES ('f4b6dabb-e19c-47d8-983d-0c0c482f1a36', 'IV-222');
INSERT INTO public.groups VALUES ('42ebaa8f-d4c9-4b8e-8962-e2657871cc42', 'IV-223');


--
-- TOC entry 3498 (class 0 OID 16725)
-- Dependencies: 220
-- Data for Name: responses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.responses VALUES ('037bac42-92fc-4789-8f40-958daa62b144', '120ce5a6-d320-4c18-b8ff-f3c8ae962a5f', 'b48b08ba-71f4-4f1c-b453-e913cdcea5ef', '.', 95, 'FILE');
INSERT INTO public.responses VALUES ('6e43d62a-cdaf-4b8b-88cb-1da07e095ee5', 'cfc4bf68-50b7-48d3-8321-992d5de83c74', 'eddbfa43-fe13-4e7e-b768-1b17194803dd', '.', 88, 'TEXT');
INSERT INTO public.responses VALUES ('d781c5a0-765e-4461-948a-30d5b1b42a9d', '20dc0a83-a114-4908-bba6-9f1a22817213', 'a0891d31-a700-4227-9567-8a7ee8880660', '.', 92, 'FILE');


--
-- TOC entry 3499 (class 0 OID 16732)
-- Dependencies: 221
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.students VALUES ('120ce5a6-d320-4c18-b8ff-f3c8ae962a5f', 'Петрович', 'Иванов', 'Сергей', 'iv221s1', 'hl832jUE', 9324782, 'sergey.ivanov@mail.com', '2003-03-15', '9f7706b2-ce24-4042-af4a-4302a23d521f');
INSERT INTO public.students VALUES ('cfc4bf68-50b7-48d3-8321-992d5de83c74', 'Игоревна', 'Сидорова', 'Елена', 'iv222s1', '3jYE7oPE', 9348275, 'elena.sidorova@mail.com', '2002-12-10', 'f4b6dabb-e19c-47d8-983d-0c0c482f1a36');
INSERT INTO public.students VALUES ('20dc0a83-a114-4908-bba6-9f1a22817213', 'Михайлович', 'Орлов', 'Дмитрий', 'iv223s1', '9Kpo32jd', 4572674, 'dmitry.orlov@mail.com', '2003-06-25', '42ebaa8f-d4c9-4b8e-8962-e2657871cc42');


--
-- TOC entry 3494 (class 0 OID 16709)
-- Dependencies: 216
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.subjects VALUES ('387bee1c-83c6-40c4-959d-5286504055f7', 'Современные технологии программирования', 'e4727d51-d25f-4e19-a6e7-dfd57334b594', '42ebaa8f-d4c9-4b8e-8962-e2657871cc42', '09:00:00', '10:30:00', '303', '2024-12-12', 'SEMINAR');
INSERT INTO public.subjects VALUES ('4e408be5-f4a5-4afe-94e2-c2e0096334bd', 'Архитектура АВС', 'cc5fa67e-2774-4fc9-a803-944d44f4019a', 'f4b6dabb-e19c-47d8-983d-0c0c482f1a36', '12:00:00', '13:30:00', '202', '2024-12-11', 'SEMINAR');
INSERT INTO public.subjects VALUES ('5a270429-4dbe-4aa4-b038-7822c968c46c', 'Сети ЭВМ и телекомуникации', '39f4a254-0619-4a30-a7d1-d49c0e7ad394', '9f7706b2-ce24-4042-af4a-4302a23d521f', '10:00:00', '11:30:00', '101', '2024-12-10', 'SEMINAR');


--
-- TOC entry 3500 (class 0 OID 16736)
-- Dependencies: 222
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tasks VALUES ('b48b08ba-71f4-4f1c-b453-e913cdcea5ef', 'Расчетно-Графическое задание', 'Собрать маршрутизатор', '2024-12-15 23:59:59', '39f4a254-0619-4a30-a7d1-d49c0e7ad394', '5a270429-4dbe-4aa4-b038-7822c968c46c');
INSERT INTO public.tasks VALUES ('eddbfa43-fe13-4e7e-b768-1b17194803dd', 'Лабораторный отчет', 'Удалить docker desktop', '2024-12-16 23:59:59', 'cc5fa67e-2774-4fc9-a803-944d44f4019a', '4e408be5-f4a5-4afe-94e2-c2e0096334bd');
INSERT INTO public.tasks VALUES ('a0891d31-a700-4227-9567-8a7ee8880660', 'Эссе', 'Написать эссе на тему, поче вы любите c++', '2024-12-17 23:59:59', 'e4727d51-d25f-4e19-a6e7-dfd57334b594', '387bee1c-83c6-40c4-959d-5286504055f7');


--
-- TOC entry 3501 (class 0 OID 16742)
-- Dependencies: 223
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.teachers VALUES ('39f4a254-0619-4a30-a7d1-d49c0e7ad394', 'Иванович', 'Петров', 'Иван', 'ivt-1', 'j83bHI7e', 7027683, 'ivan.petrov@mail.com', '1980-05-15', 'ИВТ', 'Преподаватель');
INSERT INTO public.teachers VALUES ('cc5fa67e-2774-4fc9-a803-944d44f4019a', 'Сергеевна', 'Смирнова', 'Анна', 'ivt-2', '9JG7bn60', 9385193, 'anna.smirnova@mail.com', '1985-07-20', 'ИВТ', 'Старший преподаватель');
INSERT INTO public.teachers VALUES ('e4727d51-d25f-4e19-a6e7-dfd57334b594', 'Александрович', 'Кузнецов', 'Алексей', 'ivt-3', 'K86K1d7q', 9375024, 'alexey.kuznetsov@mail.com', '1978-09-10', 'ИВТ', 'Младший преподаватель');


--
-- TOC entry 3331 (class 2606 OID 16749)
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- TOC entry 3333 (class 2606 OID 16751)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 3335 (class 2606 OID 16753)
-- Name: responses responses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.responses
    ADD CONSTRAINT responses_pkey PRIMARY KEY (id);


--
-- TOC entry 3337 (class 2606 OID 16755)
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (student_id);


--
-- TOC entry 3329 (class 2606 OID 16747)
-- Name: subjects subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (id);


--
-- TOC entry 3339 (class 2606 OID 16757)
-- Name: tasks tasks_class_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_class_id_key UNIQUE (class_id);


--
-- TOC entry 3341 (class 2606 OID 16759)
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- TOC entry 3343 (class 2606 OID 16761)
-- Name: teachers teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (teacher_id);


--
-- TOC entry 3346 (class 2606 OID 16772)
-- Name: responses responses_student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.responses
    ADD CONSTRAINT responses_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.students(student_id) ON DELETE CASCADE;


--
-- TOC entry 3347 (class 2606 OID 16777)
-- Name: responses responses_task_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.responses
    ADD CONSTRAINT responses_task_id_fkey FOREIGN KEY (task_id) REFERENCES public.tasks(id) ON DELETE CASCADE;


--
-- TOC entry 3348 (class 2606 OID 16782)
-- Name: students students_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(id) ON DELETE SET NULL;


--
-- TOC entry 3344 (class 2606 OID 16762)
-- Name: subjects subjects_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(id) ON DELETE SET NULL;


--
-- TOC entry 3345 (class 2606 OID 16767)
-- Name: subjects subjects_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teachers(teacher_id) ON DELETE SET NULL;


--
-- TOC entry 3349 (class 2606 OID 16787)
-- Name: tasks tasks_class_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_class_id_fkey FOREIGN KEY (class_id) REFERENCES public.subjects(id) ON DELETE CASCADE;


--
-- TOC entry 3350 (class 2606 OID 16792)
-- Name: tasks tasks_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teachers(teacher_id) ON DELETE SET NULL;


-- Completed on 2025-01-19 16:12:32 +07

--
-- PostgreSQL database dump complete
--

