# API and MVC Route Documentation

## Public Routes

| Method | Path | Purpose |
| --- | --- | --- |
| GET | `/` | Home page |
| GET | `/register` | Registration form |
| POST | `/register` | Create account and verification token |
| GET | `/verify-email?token=` | Verify email token |
| GET | `/login` | Login form |
| POST | `/login` | Spring Security login |
| GET | `/forgot-password` | Forgot password form |
| POST | `/forgot-password` | Create reset token |
| GET | `/reset-password?token=` | Reset password form |
| POST | `/reset-password` | Update password |

## Authenticated User Routes

| Method | Path | Purpose |
| --- | --- | --- |
| GET | `/dashboard?city=London` | Dashboard with weather, forecast, AI summary, and analytics |
| GET | `/weather` | City weather search page |
| POST | `/weather/search` | Search weather by city |
| GET | `/favorites?userId=1` | Favorite cities view |
| GET | `/cities/search?keyword=Lon` | JSON city search |

## Admin Routes

| Method | Path | Purpose |
| --- | --- | --- |
| GET | `/admin` | Admin statistics dashboard |

## External Integrations

- OpenWeather current weather: `/data/2.5/weather`
- OpenWeather forecast: `/data/2.5/forecast`
- Spring AI OpenAI chat model for recommendation text

## Security Notes

- CSRF is enabled.
- Login uses email as the username field.
- Passwords are stored with BCrypt.
- `/admin/**` requires `ROLE_ADMIN`.
- Other application routes require authentication.
