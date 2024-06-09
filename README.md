# Exchange Rate API

Esta API proporciona operaciones para manejar las tasas de cambio entre diferentes monedas.
Puedes visualizar el proyecto en vivo en el siguiente enlace: https://currency-api-front.vercel.app
Backend: https://currencyexchangeapi-production.up.railway.app/webjars/swagger-ui/index.html
## Controladores

El controlador `ExchangeRateController` maneja las operaciones relacionadas con las tasas de cambio.

### Endpoints

- **POST /exchange-rate/change**: Convierte una cantidad de una moneda a otra.
- **GET /exchange-rate/{id}**: Obtiene una tasa de cambio por su identificador.
- **GET /exchange-rate**: Obtiene todas las tasas de cambio con paginación.
- **GET /exchange-rate/search**: Obtiene las tasas de cambio por moneda de origen y moneda de destino.

### Autorización

Para acceder a los endpoints del controlador de tasas de cambio, se requiere un token de autorización que se genera en el endpoint `/auth/token` del controlador `AuthController`.

### Setup

1. Define una variable de entorno `JWT_SECRET` con la clave secreta para la generación de tokens JWT.
 
> **_NOTA:_**  Tu llave privada debe cumplir con el minimo de caracteres 
validos para una encriptación HS256.

2. Inicia la aplicación con Docker:
- Contruye la imagen:
```cmd
docker build -t exchange-rate-api .
```
- Ejecuta el contenedor:
```cmd
docker run -p 8080:8080 -e JWT_SECRET=your_jwt_secret exchange-rate-api

```

### Documentación
#### Swagger [Click to visit](https://currencyexchangeapi-production.up.railway.app/webjars/swagger-ui/index.html)
La documentación en swagger la podrás acceder en la siguiente ruta:

- `localhost:8080/webjars/swagger-ui/index.html`
#### Javadocs

La documentación Javadocs está generada en el directorio raíz del proyecto. Para acceder a ella, abre el archivo `index.html` ubicado en:

- `{root_dir}/docs/index.html`

