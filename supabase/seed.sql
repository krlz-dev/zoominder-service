-- Seed data for development/testing

-- Insert test user (password: 'password123' hashed with bcrypt)
INSERT INTO public.users (id, email, password_hash, first_name, last_name)
VALUES
  ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'test@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Test', 'User'),
  ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'john@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'John', 'Doe');

-- Insert test pets
INSERT INTO public.pets (id, user_id, name, weight_kg, birthdate)
VALUES
  ('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Buddy', 25.5, '2020-03-15'),
  ('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Luna', 4.2, '2021-07-22'),
  ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Max', 30.0, '2019-01-10');

-- Insert test medications
INSERT INTO public.medications (id, user_id, pet_id, name, amount, unit, frequency_type, times, is_active, start_date)
VALUES
  ('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Heartgard', 1, 'tablet', 'monthly', '08:00', true, '2024-01-01'),
  ('f1eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Feliway', 1, 'spray', 'daily', '09:00,21:00', true, '2024-01-01'),
  ('f2eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Apoquel', 16, 'mg', 'daily', '07:00', true, '2024-01-15');
