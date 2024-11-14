Shipping Tracker API
Shipping Tracker é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar o rastreamento de pacotes. Este projeto simula o funcionamento de um sistema de rastreamento de encomendas, permitindo que os usuários consultem, atualizem e excluam pacotes pelo número de rastreamento, além de listar todos os pacotes cadastrados.

Funcionalidades
Consultar Pacote pelo Número de Rastreamento: Busca detalhes de um pacote com base no número de rastreamento.
Cadastrar Novo Pacote: Registra novos pacotes no sistema com status inicial e localização.
Atualizar Status do Pacote: Permite a atualização do status de entrega.
Excluir Pacote: Remove pacotes específicos do sistema.
Listar Todos os Pacotes: Exibe todos os pacotes registrados.
Tecnologias Utilizadas
Java 17 e Spring Boot
JPA/Hibernate para persistência de dados
SQL Server como banco de dados relacional
Spring Data JPA para operações CRUD
Spring REST para construção da API RESTful
Tratamento de Exceções Personalizadas
Diagrama de Classes
Abaixo está o diagrama de classes representando a estrutura da aplicação. Ele exibe as principais classes e suas relações.

mermaid
Copiar código
classDiagram
    class Parcel {
        Long id
        String trackingNumber
        String status
        String location
        Date lastUpdate
        +getId()
        +setId(Long id)
        +getTrackingNumber()
        +setTrackingNumber(String trackingNumber)
        +getStatus()
        +setStatus(String status)
        +getLocation()
        +setLocation(String location)
        +getLastUpdate()
        +setLastUpdate(Date lastUpdate)
    }

    class ParcelController {
        -ParcelService service
        +getParcelByTrackingNumber(String trackingNumber): Parcel
        +createParcel(Parcel parcel): Parcel
        +updateParcelStatus(String trackingNumber, String status): Parcel
        +deleteParcel(String trackingNumber)
        +getAllParcels(): List~Parcel~
    }

    class ParcelService {
        -ParcelRepository repository
        +getParcelByTrackingNumber(String trackingNumber): Parcel
        +saveParcel(Parcel parcel): Parcel
        +updateParcelStatus(String trackingNumber, String status): Parcel
        +deleteParcel(String trackingNumber)
        +getAllParcels(): List~Parcel~
    }

    class ParcelRepository {
        +findByTrackingNumber(String trackingNumber): Optional~Parcel~
    }

    class GlobalExceptionHandler {
        +handleTrackingNumberNotFoundException(TrackingNumberNotFoundException ex): ResponseEntity~String~
        +handleParcelValidationException(ParcelValidationException ex): ResponseEntity~String~
        +handleGeneralException(Exception ex): ResponseEntity~String~
    }

    class TrackingNumberNotFoundException {
        -String message
        +TrackingNumberNotFoundException(String trackingNumber)
    }

    class ParcelValidationException {
        -String message
        +ParcelValidationException(String message)
    }

    ParcelController --> ParcelService
    ParcelService --> ParcelRepository
    ParcelRepository --> Parcel
    GlobalExceptionHandler --> TrackingNumberNotFoundException
    GlobalExceptionHandler --> ParcelValidationException
    ParcelService --> TrackingNumberNotFoundException
    ParcelService --> ParcelValidationException
Endpoints
GET /api/parcels/{trackingNumber}: Consulta o pacote pelo número de rastreamento.
POST /api/parcels: Cria um novo pacote.
PUT /api/parcels/{trackingNumber}/status: Atualiza o status do pacote.
DELETE /api/parcels/{trackingNumber}: Exclui um pacote.
GET /api/parcels: Lista todos os pacotes.
Como Executar o Projeto
Clone este repositório.
Configure seu banco de dados SQL Server e ajuste as credenciais no application.properties.
Compile e execute o projeto usando a IDE de sua preferência ou via terminal com mvn spring-boot:run.
Tratamento de Exceções
A API utiliza um controlador global de exceções (GlobalExceptionHandler) que trata:

TrackingNumberNotFoundException: Quando o número de rastreamento não é encontrado.
ParcelValidationException: Quando há validações incorretas nos dados do pacote.
Exception: Para capturar erros gerais da aplicação.
Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request ou relatar problemas na seção de Issues.

