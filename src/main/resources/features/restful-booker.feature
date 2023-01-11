Feature: Booker APIs

  Scenario: Creación de Token
    Given El usuario esta registrado en el api
    When El usuario ingresa las credenciales para generar el token
      |username|password|
      |admin   |password123|
    Then Verificamos que el servicio responsa con status code "200"
    And Verificamos la respuesta tenga el toke
    And Verificamos que el token contenga solo caracteres alfanumericos


  Scenario: El token debe de tener 15 caracteres y ser alfanumerico
    Given El usuario esta registrado en el api
    When El usuario ingresa las credenciales para generar el token
      |username|password|
      |admin   |password123|
    Then Verificamos que el servicio responsa con status code "200"
    And Verificamos que el token debe de tener "15" caracteres
    And Verificamos que el token contenga solo caracteres alfanumericos

  Scenario: Se obtiene toda la información del get bookingids
    Given El usuario accede al api getBookings
    When Solicita toda la información de los bookingid
    And Se muestra toda la información que obtiene el servicio

  Scenario: El servicio get bookingids obtiene toda la información con filtro de nombres
    Given El usuario accede al api getBookings
    When Se soilicita toda la información filtrando por los siguientes nombres
      |firstname|lastname  |
      |Jane     |Doe       |
    And Se muestra toda la información que obtiene el servicio

  Scenario: El servicio get bookingids obtiene toda la información con filtros de checkin y checkout
    Given El usuario accede al api getBookings
    When  Se solicita toda la información filtrando con la fecha de checkin y checkout
      |checkin        |checkout         |
      |2020-03-08     |2022-10-10       |
    And Se muestra toda la información que obtiene el servicio

  Scenario: El usuario crea un nuevo booking
    Given El booking no esta creado
    When  Accede al api para crear un nuevo booking
      |firstname|lastname|totalprice|depositpaid|checkin   |checkout  |additionalneeds|
      |Manuel   |Rosales |500       |true       |2022-01-10|2022-02-25|Dinner    |
    And Verificamos que se obtenga en la respuesta el bookingid

  Scenario: Validar update total de nuevo Booking
    Given El booking no esta creado
    When Accede al api para crear un nuevo booking
      |firstname|lastname |totalprice|checkin   |checkout |additionalneeds|
      |Martin   |garcia   |1000      |2023-02-10|203-02-15|Dinner         |
    And  Verificamos que se obtenga en la respuesta el bookingid
    When El usuario ingresa las credenciales para generar el token
      |username|password|
      |admin   |password123|
    And Verificamos la respuesta tenga el toke
    Given Realizo actualizacion de booking
    When Accede para realizar una actualización total de los datos
      |firstname|lastname |totalprice|checkin   |checkout |additionalneeds|
      |Manuel   |Rosales  |500       |2023-03-15|203-04-15|Lunch          |
    And Verificamos que se realizo la actualización

  Scenario: Validar update parcial de nuevo Booking
    Given El booking no esta creado
    When Accede al api para crear un nuevo booking
      |firstname|lastname|totalprice|checkin|checkout|additionalneeds|
      |Manuel   |Rosales |5000      |2023-01-02|203-01-08|Lunch      |
    And  Verificamos que se obtenga en la respuesta el bookingid
    When El usuario ingresa las credenciales para generar el token
      |username|password|
      |admin   |password123|
    And Verificamos la respuesta tenga el toke
    Given Realizo actualizacion de booking
    When Accede para realizar una actualización parcial de los datos
      |firstname|lastname|
      |Juan     |Ramon |
    And Verificamos que se realizo la actualización

  Scenario: Realizar la eliminacón de un booking creado
    Given El booking no esta creado
    When Accede al api para crear un nuevo booking
      |firstname|lastname|totalprice|checkin|checkout|additionalneeds|
      |Manuel   |Rosales |5000      |2023-01-02|203-01-08|Lunch      |
    And  Verificamos que se obtenga en la respuesta el bookingid
    When El usuario ingresa las credenciales para generar el token
      |username|password|
      |admin   |password123|
    And Verificamos la respuesta tenga el toke
    Given El usuario ejecuta el servicio
    When Accedemos al api para realizar la eliminación

  Scenario: Ping HelthCheck
    Given El usuario ejecuta el servicio
    When  Accede al servicio HealthCheck
    And Verificamos que el servicio este con status code "201"






