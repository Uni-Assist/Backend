--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6 (Ubuntu 16.6-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.6 (Ubuntu 16.6-0ubuntu0.24.04.1)

-- Started on 2025-03-06 17:33:29 +07

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
-- TOC entry 2 (class 3079 OID 16884)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 3528 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 922 (class 1247 OID 17164)
-- Name: Department; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."Department" AS ENUM (
    'EEF',
    'ATC',
    'CSE',
    'MTS',
    'DL',
    'MRM',
    'HF'
);


--
-- TOC entry 919 (class 1247 OID 17156)
-- Name: LessonType; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."LessonType" AS ENUM (
    'LECTURE',
    'PRACTICE',
    'LABORATORY'
);


--
-- TOC entry 886 (class 1247 OID 16922)
-- Name: ResponseType; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."SolutionType" AS ENUM (
    'TEXT',
    'FILE'
);


--
-- TOC entry 264 (class 1255 OID 17903)
-- Name: create_lesson_partition(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION public.create_lesson_partition() RETURNS trigger
    LANGUAGE plpgsql
AS $$
DECLARE
    partition_name TEXT;
    partition_start DATE;
    partition_end DATE;
BEGIN
    partition_start := date_trunc('month', NEW.date)::DATE;
    partition_end := (partition_start + INTERVAL '1 month')::DATE;
    partition_name := 'lessons_' || to_char(partition_start, 'YYYY_MM');

    IF NOT EXISTS (
        SELECT 1 FROM pg_partitioned_table pt
                          JOIN pg_class c ON pt.partrelid = c.oid
                          JOIN pg_namespace n ON c.relnamespace = n.oid
        WHERE c.relname = partition_name AND n.nspname = 'public'
    ) THEN
        EXECUTE format(
                'CREATE TABLE %I PARTITION OF lessons FOR VALUES FROM (%L) TO (%L)',
                partition_name, partition_start, partition_end
                );
    END IF;

    RETURN NEW;
END;
$$;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16933)
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
-- TOC entry 217 (class 1259 OID 16938)
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


--
-- TOC entry 218 (class 1259 OID 16941)
-- Name: groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.groups (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(15) NOT NULL,
    department public."Department" NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 17048)
-- Name: lessons; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.lessons (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    schedule_id uuid NOT NULL,
    start_time time without time zone NOT NULL,
    end_time time without time zone NOT NULL,
    classroom character varying(10) NOT NULL,
    date date NOT NULL
) PARTITION BY RANGE (date);


--
-- TOC entry 220 (class 1259 OID 17920)
-- Name: lessons_2024_12; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.lessons_2024_12 (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    schedule_id uuid NOT NULL,
    start_time time without time zone NOT NULL,
    end_time time without time zone NOT NULL,
    classroom character varying(10) NOT NULL,
    date date NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 17924)
-- Name: lessons_2025_03; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.lessons_2025_03 (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    schedule_id uuid NOT NULL,
    start_time time without time zone NOT NULL,
    end_time time without time zone NOT NULL,
    classroom character varying(10) NOT NULL,
    date date NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 17932)
-- Name: responses; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.solutions (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    student_id uuid NOT NULL,
    task_id uuid NOT NULL,
    body text,
    mark integer,
    type public."SolutionType" NOT NULL,
    CONSTRAINT solutions_mark_check CHECK (((mark >= 0) AND (mark <= 100)))
);


--
-- TOC entry 223 (class 1259 OID 17939)
-- Name: schedules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.schedules (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    subject_id uuid NOT NULL,
    group_id uuid NOT NULL,
    teacher_id uuid NOT NULL,
    type public."LessonType" NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 18891)
-- Name: solutions; Type: TABLE; Schema: public; Owner: -
--


--
-- TOC entry 222 (class 1259 OID 18898)
-- Name: students; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.students (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    middle_name character varying(15),
    last_name character varying(15),
    first_name character varying(15) NOT NULL,
    login character varying(15) NOT NULL,
    password character varying(60) NOT NULL,
    phone character varying(20) NOT NULL,
    email character varying(30) NOT NULL,
    birthdate date NOT NULL,
    group_id uuid NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 17021)
-- Name: subjects; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.subjects (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(40) NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16960)
-- Name: tasks; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tasks (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    header character varying(40) NOT NULL,
    body text NOT NULL,
    due_time timestamp without time zone NOT NULL,
    teacher_id uuid,
    lesson_id uuid
);


--
-- TOC entry 222 (class 1259 OID 16966)
-- Name: teachers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.teachers (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    middle_name character varying(15),
    last_name character varying(15),
    first_name character varying(15) NOT NULL,
    login character varying(15) NOT NULL,
    password character varying(60) NOT NULL,
    phone character varying(20) NOT NULL,
    email character varying(30) NOT NULL,
    birthdate date NOT NULL,
    job_title character varying(40) NOT NULL,
    department public."Department" NOT NULL
);


--
-- TOC entry 3341 (class 0 OID 0)
-- Name: lessons_2024_12; Type: TABLE ATTACH; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lessons ATTACH PARTITION public.lessons_2024_12 FOR VALUES FROM ('2024-12-01') TO ('2025-01-01');


--
-- TOC entry 3342 (class 0 OID 0)
-- Name: lessons_2025_03; Type: TABLE ATTACH; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lessons ATTACH PARTITION public.lessons_2025_03 FOR VALUES FROM ('2025-03-01') TO ('2025-04-01');


--
-- TOC entry 3532 (class 0 OID 17904)
-- Dependencies: 216
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3514 (class 0 OID 16938)
-- Dependencies: 217
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.databasechangeloglock (id, locked, lockgranted, lockedby) VALUES (1, false, NULL, NULL);


--
-- TOC entry 3515 (class 0 OID 16941)
-- Dependencies: 218
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.groups (id, name, department) VALUES ('42ebaa8f-d4c9-4b8e-8962-e2657871cc42', 'ИВ-223', 'CSE');
INSERT INTO public.groups (id, name, department) VALUES ('9f7706b2-ce24-4042-af4a-4302a23d521f', 'ИВ-221', 'CSE');
INSERT INTO public.groups (id, name, department) VALUES ('f4b6dabb-e19c-47d8-983d-0c0c482f1a36', 'ИВ-222', 'CSE');

--
-- TOC entry 3516 (class 0 OID 16945)
-- Dependencies: 219
-- Data for Name: responses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.lessons (id, schedule_id, start_time, end_time, classroom, date) VALUES ('fd2942a4-abc3-4790-ba44-79a6c511a5c1', '67f0ffe0-3f40-4ad4-9610-3996dda5a81a', '09:00:00', '10:30:00', '303', '2024-12-12');
INSERT INTO public.lessons (id, schedule_id, start_time, end_time, classroom, date) VALUES ('afc81c75-0dfa-4576-a563-f572bbb8ab33', 'c709e770-4538-479f-8449-6d6de29754cd', '12:00:00', '13:30:00', '202', '2024-12-11');
INSERT INTO public.lessons (id, schedule_id, start_time, end_time, classroom, date) VALUES ('e01733c0-61f3-4d71-9713-15d874c2cd10', 'aaa15d2e-1b85-4ee1-999f-0dcd7ccfc5b5', '10:00:00', '11:30:00', '101', '2024-12-10');


--
-- TOC entry 3521 (class 0 OID 17027)
-- Dependencies: 224
-- Data for Name: schedules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.schedules (id, subject_id, group_id, teacher_id, type) VALUES ('67f0ffe0-3f40-4ad4-9610-3996dda5a81a', 'db8c7352-c723-4dbe-b37c-66edbcb9dcca', '42ebaa8f-d4c9-4b8e-8962-e2657871cc42', 'e4727d51-d25f-4e19-a6e7-dfd57334b594', 'PRACTICE');
INSERT INTO public.schedules (id, subject_id, group_id, teacher_id, type) VALUES ('aaa15d2e-1b85-4ee1-999f-0dcd7ccfc5b5', '1743862a-7833-43e7-8321-2ac5eff2ee3e', '9f7706b2-ce24-4042-af4a-4302a23d521f', '39f4a254-0619-4a30-a7d1-d49c0e7ad394', 'PRACTICE');
INSERT INTO public.schedules (id, subject_id, group_id, teacher_id, type) VALUES ('c709e770-4538-479f-8449-6d6de29754cd', 'b5edeadc-1026-42b9-9fc0-090e26f06e54', 'f4b6dabb-e19c-47d8-983d-0c0c482f1a36', 'cc5fa67e-2774-4fc9-a803-944d44f4019a', 'PRACTICE');


--
-- TOC entry 3518 (class 0 OID 18891)
-- Dependencies: 221
-- Data for Name: solutions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.solutions (id, student_id, task_id, body, mark, type) VALUES ('037bac42-92fc-4789-8f40-958daa62b144', '120ce5a6-d320-4c18-b8ff-f3c8ae962a5f', 'b48b08ba-71f4-4f1c-b453-e913cdcea5ef', '.', 95, 'FILE');
INSERT INTO public.solutions (id, student_id, task_id, body, mark, type) VALUES ('6e43d62a-cdaf-4b8b-88cb-1da07e095ee5', 'cfc4bf68-50b7-48d3-8321-992d5de83c74', 'eddbfa43-fe13-4e7e-b768-1b17194803dd', '.', 88, 'TEXT');
INSERT INTO public.solutions (id, student_id, task_id, body, mark, type) VALUES ('d781c5a0-765e-4461-948a-30d5b1b42a9d', '20dc0a83-a114-4908-bba6-9f1a22817213', 'a0891d31-a700-4227-9567-8a7ee8880660', '.', 92, 'FILE');


--
-- TOC entry 3519 (class 0 OID 18898)
-- Dependencies: 222
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.students (id, middle_name, last_name, first_name, login, password, phone, email, birthdate, group_id) VALUES ('120ce5a6-d320-4c18-b8ff-f3c8ae962a5f', 'Петрович', 'Иванов', 'Сергей', 'iv221s1', '$2a$10$Ya/hiEzymCKoFQXzp6Ykgu5UUyOGQkILnotpTFKwbmlfsydFGBbuO', '89139324782', 'sergey.ivanov@mail.com', '2003-03-15', '9f7706b2-ce24-4042-af4a-4302a23d521f');
INSERT INTO public.students (id, middle_name, last_name, first_name, login, password, phone, email, birthdate, group_id) VALUES ('20dc0a83-a114-4908-bba6-9f1a22817213', 'Михайлович', 'Орлов', 'Дмитрий', 'iv223s1', '$2a$10$VKxYNel1BWwbvGCD.nrklueFallu1dq8A0cEooy/GDuVRefNXtXJm', '+79134572674', 'dmitry.orlov@mail.com', '2003-06-25', '42ebaa8f-d4c9-4b8e-8962-e2657871cc42');
INSERT INTO public.students (id, middle_name, last_name, first_name, login, password, phone, email, birthdate, group_id) VALUES ('cfc4bf68-50b7-48d3-8321-992d5de83c74', 'Игоревна', 'Сидорова', 'Елена', 'iv222s1', '$2a$10$1WPN7FIpJ2Zk1F7Ou0GEaOqwMgl8McqnG0SEAtxJmuLB25zLbuVre', '89239348275', 'elena.sidorova@mail.com', '2002-12-10', 'f4b6dabb-e19c-47d8-983d-0c0c482f1a36');


--
-- TOC entry 3520 (class 0 OID 17021)
-- Dependencies: 223
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.subjects (id, name) VALUES ('1743862a-7833-43e7-8321-2ac5eff2ee3e', 'Сети ЭВМ и телекомуникации');
INSERT INTO public.subjects (id, name) VALUES ('b5edeadc-1026-42b9-9fc0-090e26f06e54', 'Архитектура АВС');
INSERT INTO public.subjects (id, name) VALUES ('db8c7352-c723-4dbe-b37c-66edbcb9dcca', 'Современные технологии программирования');


--
-- TOC entry 3518 (class 0 OID 16960)
-- Dependencies: 221
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tasks (id, header, body, due_time, teacher_id, lesson_id) VALUES ('a0891d31-a700-4227-9567-8a7ee8880660', 'Эссе', 'Написать эссе на тему, поче вы любите c++', '2024-12-17 23:59:59', 'e4727d51-d25f-4e19-a6e7-dfd57334b594', 'fd2942a4-abc3-4790-ba44-79a6c511a5c1');
INSERT INTO public.tasks (id, header, body, due_time, teacher_id, lesson_id) VALUES ('b48b08ba-71f4-4f1c-b453-e913cdcea5ef', 'Расчетно-Графическое задание', 'Собрать маршрутизатор', '2024-12-15 23:59:59', '39f4a254-0619-4a30-a7d1-d49c0e7ad394', 'e01733c0-61f3-4d71-9713-15d874c2cd10');
INSERT INTO public.tasks (id, header, body, due_time, teacher_id, lesson_id) VALUES ('eddbfa43-fe13-4e7e-b768-1b17194803dd', 'Лабораторный отчет', 'Удалить docker desktop', '2024-12-16 23:59:59', 'cc5fa67e-2774-4fc9-a803-944d44f4019a', 'afc81c75-0dfa-4576-a563-f572bbb8ab33');


--
-- TOC entry 3519 (class 0 OID 16966)
-- Dependencies: 222
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.teachers (id, middle_name, last_name, first_name, login, password, phone, email, birthdate, job_title, department) VALUES ('39f4a254-0619-4a30-a7d1-d49c0e7ad394', 'Иванович', 'Петров', 'Иван', 'ivt-1', '$2a$10$ChWI3y5eo1xirdq8eWnBhu4A3X5A/uWljW3.Y43B09hm6qfyAi8xW', '+79136307529', 'ivan.petrov@mail.com', '1980-05-15', 'Преподаватель', 'CSE');
INSERT INTO public.teachers (id, middle_name, last_name, first_name, login, password, phone, email, birthdate, job_title, department) VALUES ('cc5fa67e-2774-4fc9-a803-944d44f4019a', 'Сергеевна', 'Смирнова', 'Анна', 'ivt-2', '$2a$10$jHxjW.x9ATIERCm9WWyElO6wfEgLRZ6V.ZbAGpUqgFUaApU2n2/Ua', '89136402306', 'anna.smirnova@mail.com', '1985-07-20', 'Старший преподаватель', 'CSE');
INSERT INTO public.teachers (id, middle_name, last_name, first_name, login, password, phone, email, birthdate, job_title, department) VALUES ('e4727d51-d25f-4e19-a6e7-dfd57334b594', 'Александрович', 'Кузнецов', 'Алексей', 'ivt-3', '$2a$10$Wdw7xvXsAl7reO0p452cfuUg2bVgOHOVn.zfRr6/GKcof190lkzhW', '89230907642', 'alexey.kuznetsov@mail.com', '1978-09-10', 'Младший преподаватель', 'CSE');


--
-- TOC entry 3342 (class 2606 OID 16971)
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- TOC entry 3344 (class 2606 OID 16973)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 3360 (class 2606 OID 17053)
-- Name: lessons lessons_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT lessons_pkey PRIMARY KEY (id, date);


--
-- TOC entry 3361 (class 2606 OID 17968)
-- Name: lessons_2024_12 lessons_2024_12_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lessons_2024_12
    ADD CONSTRAINT lessons_2024_12_pkey PRIMARY KEY (id, date);


--
-- TOC entry 3363 (class 2606 OID 17970)
-- Name: lessons_2025_03 lessons_2025_03_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.lessons_2025_03
    ADD CONSTRAINT lessons_2025_03_pkey PRIMARY KEY (id, date);


--
-- TOC entry 3348 (class 2606 OID 18923)
-- Name: schedules schedules_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT schedules_pkey PRIMARY KEY (id);


--
-- TOC entry 3350 (class 2606 OID 18925)
-- Name: solutions solutions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_pkey PRIMARY KEY (id);


--
-- TOC entry 3352 (class 2606 OID 18927)
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- TOC entry 3356 (class 2606 OID 17026)
-- Name: subjects subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (id);


--
-- TOC entry 3350 (class 2606 OID 16981)
-- Name: tasks tasks_lesson_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_lesson_id_key UNIQUE (lesson_id);


--
-- TOC entry 3352 (class 2606 OID 16983)
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- TOC entry 3354 (class 2606 OID 16985)
-- Name: teachers teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (id);


--
-- TOC entry 3378 (class 0 OID 0)
-- Name: lessons_2024_12_pkey; Type: INDEX ATTACH; Schema: public; Owner: -
--

ALTER INDEX public.lessons_pkey ATTACH PARTITION public.lessons_2024_12_pkey;


--
-- TOC entry 3379 (class 0 OID 0)
-- Name: lessons_2025_03_pkey; Type: INDEX ATTACH; Schema: public; Owner: -
--

ALTER INDEX public.lessons_pkey ATTACH PARTITION public.lessons_2025_03_pkey;


--
-- TOC entry 3388 (class 2620 OID 17987)
-- Name: lessons lessons_partition_trigger; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER lessons_partition_trigger BEFORE INSERT ON public.lessons FOR EACH ROW EXECUTE FUNCTION public.create_lesson_partition();


--
-- TOC entry 3380 (class 2606 OID 17990)
-- Name: lessons lessons_schedule_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE public.lessons
    ADD CONSTRAINT lessons_schedule_id_fkey FOREIGN KEY (schedule_id) REFERENCES public.schedules(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3366 (class 2606 OID 17079)
-- Name: schedules schedules_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT schedules_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3367 (class 2606 OID 17033)
-- Name: schedules schedules_subject_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT schedules_subject_id_fkey FOREIGN KEY (subject_id) REFERENCES public.subjects(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3368 (class 2606 OID 17084)
-- Name: schedules schedules_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT schedules_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teachers(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3365 (class 2606 OID 18956)
-- Name: solutions solutions_student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.students(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3366 (class 2606 OID 18961)
-- Name: solutions solutions_task_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_task_id_fkey FOREIGN KEY (task_id) REFERENCES public.tasks(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3367 (class 2606 OID 18966)
-- Name: students students_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3387 (class 2606 OID 18031)
-- Name: tasks tasks_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teachers(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2025-03-06 17:33:29 +07

--
-- PostgreSQL database dump complete
--

