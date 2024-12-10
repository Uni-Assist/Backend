--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4 (Ubuntu 16.4-0ubuntu0.24.04.2)
-- Dumped by pg_dump version 16.4 (Ubuntu 16.4-0ubuntu0.24.04.2)

-- Started on 2024-12-10 19:33:54 +07

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
-- TOC entry 2 (class 3079 OID 16401)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 3491 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 900 (class 1247 OID 16582)
-- Name: ClassType; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."ClassType" AS ENUM (
    'lecture',
    'seminar'
);


--
-- TOC entry 903 (class 1247 OID 16588)
-- Name: ResponseType; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."ResponseType" AS ENUM (
    'text',
    'file'
);


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 16510)
-- Name: classes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.classes (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    subject character varying(40) NOT NULL,
    teacher_id uuid NOT NULL,
    group_id uuid NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL,
    classroom character varying(10) NOT NULL,
    date date NOT NULL,
    type character varying(255) NOT NULL,
    name character varying(255)
);


--
-- TOC entry 216 (class 1259 OID 16438)
-- Name: groups; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.groups (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(15) NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16549)
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
-- TOC entry 217 (class 1259 OID 16444)
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
    group_id uuid
);


--
-- TOC entry 220 (class 1259 OID 16529)
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
-- TOC entry 218 (class 1259 OID 16481)
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
    department character varying(30) NOT NULL
);


--
-- TOC entry 3327 (class 2606 OID 16518)
-- Name: classes classes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.classes
    ADD CONSTRAINT classes_pkey PRIMARY KEY (id);


--
-- TOC entry 3321 (class 2606 OID 16443)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 3333 (class 2606 OID 16558)
-- Name: responses responses_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.responses
    ADD CONSTRAINT responses_pkey PRIMARY KEY (id);


--
-- TOC entry 3335 (class 2606 OID 16560)
-- Name: responses responses_task_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.responses
    ADD CONSTRAINT responses_task_id_key UNIQUE (task_id);


--
-- TOC entry 3323 (class 2606 OID 16449)
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (student_id);


--
-- TOC entry 3329 (class 2606 OID 16538)
-- Name: tasks tasks_class_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_class_id_key UNIQUE (class_id);


--
-- TOC entry 3331 (class 2606 OID 16536)
-- Name: tasks tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- TOC entry 3325 (class 2606 OID 16489)
-- Name: teachers teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (teacher_id);


--
-- TOC entry 3337 (class 2606 OID 16524)
-- Name: classes classes_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.classes
    ADD CONSTRAINT classes_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(id) ON DELETE SET NULL;


--
-- TOC entry 3338 (class 2606 OID 16519)
-- Name: classes classes_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.classes
    ADD CONSTRAINT classes_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teachers(teacher_id) ON DELETE SET NULL;


--
-- TOC entry 3341 (class 2606 OID 16561)
-- Name: responses responses_student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.responses
    ADD CONSTRAINT responses_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.students(student_id) ON DELETE CASCADE;


--
-- TOC entry 3342 (class 2606 OID 16566)
-- Name: responses responses_task_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.responses
    ADD CONSTRAINT responses_task_id_fkey FOREIGN KEY (task_id) REFERENCES public.tasks(id) ON DELETE CASCADE;


--
-- TOC entry 3336 (class 2606 OID 16450)
-- Name: students students_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.groups(id) ON DELETE SET NULL;


--
-- TOC entry 3339 (class 2606 OID 16544)
-- Name: tasks tasks_class_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_class_id_fkey FOREIGN KEY (class_id) REFERENCES public.classes(id) ON DELETE CASCADE;


--
-- TOC entry 3340 (class 2606 OID 16539)
-- Name: tasks tasks_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teachers(teacher_id) ON DELETE SET NULL;


-- Completed on 2024-12-10 19:33:54 +07

--
-- PostgreSQL database dump complete
--

