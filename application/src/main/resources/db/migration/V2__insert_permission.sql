INSERT INTO public.permission_group (id, group_name)
VALUES ('1', 'ADMIN');
INSERT INTO public.permission_group (id, group_name)
VALUES (gen_random_uuid(), 'USER');

INSERT INTO public.permission (id, user_email, type, permission_group_id)
VALUES (gen_random_uuid(), 'view@gmail.com', 'VIEW', '1');
INSERT INTO public.permission (id, user_email, type, permission_group_id)
VALUES (gen_random_uuid(), 'edit@gmail.com', 'EDIT', '1');