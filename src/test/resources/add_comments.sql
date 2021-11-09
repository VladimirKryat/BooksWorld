insert into comment (stars, text, user_id)
select 4, 'Default comment', user_id
from userdata where username='Vladimir'
union
select 1, 'Default comment by Admin', user_id
from userdata where username='VladimirKryat'
union
select 5, null, user_id
from userdata where username='VladimirKryat'
union
select 2, null, user_id
from userdata where username='Vladimir';