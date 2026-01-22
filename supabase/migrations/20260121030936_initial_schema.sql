-- Helper function for updating timestamps
create or replace function update_updated_at_column()
returns trigger as $$
begin
  new.updated_at = current_timestamp;
  return new;
end;
$$ language plpgsql;

-- Helper function for deleting medication logs when pet is deleted
create or replace function delete_medication_logs_on_pet_delete()
returns trigger as $$
begin
  delete from medication_logs where pet_id = old.id;
  return old;
end;
$$ language plpgsql;

-- Users table
create table public.users (
  id uuid not null default gen_random_uuid(),
  email character varying(255) not null,
  password_hash text not null,
  created_at timestamp with time zone null default current_timestamp,
  updated_at timestamp with time zone null default current_timestamp,
  first_name character varying(100) null,
  last_name character varying(100) null,
  constraint users_pkey primary key (id),
  constraint users_email_key unique (email),
  constraint email_format check (
    (email)::text ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'
  )
);

create index if not exists idx_users_email on public.users using btree (email);

create trigger update_users_updated_at
  before update on users
  for each row
  execute function update_updated_at_column();

-- Pets table
create table public.pets (
  id uuid not null default gen_random_uuid(),
  user_id uuid not null,
  name character varying(100) not null,
  weight_kg numeric(5, 2) null,
  birthdate date null,
  created_at timestamp with time zone null default current_timestamp,
  updated_at timestamp with time zone null default current_timestamp,
  photo_data bytea null,
  constraint pets_pkey primary key (id),
  constraint fk_user foreign key (user_id) references users (id) on delete cascade
);

create index if not exists idx_pets_user_id on public.pets using btree (user_id);

create trigger update_pets_updated_at
  before update on pets
  for each row
  execute function update_updated_at_column();

-- Medications table
create table public.medications (
  id uuid not null default gen_random_uuid(),
  user_id uuid not null,
  pet_id uuid not null,
  name character varying(200) not null,
  amount numeric(10, 2) not null,
  unit character varying(50) not null,
  frequency_type character varying(20) not null,
  selected_weekdays text[] null,
  day_of_month integer null,
  specific_date date null,
  times text not null,
  notes text null,
  is_active boolean null default true,
  created_at timestamp with time zone null default current_timestamp,
  updated_at timestamp with time zone null default current_timestamp,
  start_date date null,
  end_date date null,
  duration_type character varying(20) null,
  duration_value integer null,
  constraint medications_pkey primary key (id),
  constraint fk_pet foreign key (pet_id) references pets (id) on delete cascade,
  constraint fk_user foreign key (user_id) references users (id) on delete cascade
);

create index if not exists idx_medications_user_id on public.medications using btree (user_id);
create index if not exists idx_medications_pet_id on public.medications using btree (pet_id);

create trigger update_medications_updated_at
  before update on medications
  for each row
  execute function update_updated_at_column();

-- Medication logs table (for tracking administered medications)
create table public.medication_logs (
  id uuid not null default gen_random_uuid(),
  medication_id uuid not null,
  pet_id uuid not null,
  administered_at timestamp with time zone not null default current_timestamp,
  notes text null,
  constraint medication_logs_pkey primary key (id),
  constraint fk_medication foreign key (medication_id) references medications (id) on delete cascade,
  constraint fk_pet foreign key (pet_id) references pets (id) on delete cascade
);

create index if not exists idx_medication_logs_medication_id on public.medication_logs using btree (medication_id);
create index if not exists idx_medication_logs_pet_id on public.medication_logs using btree (pet_id);

-- Add the pet delete trigger after medication_logs table exists
create trigger trigger_delete_medication_logs_on_pet_delete
  before delete on pets
  for each row
  execute function delete_medication_logs_on_pet_delete();
