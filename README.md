# SOLID Principles Lab - Java

## Integrantes del grupo

- Cristian Camilo Gomez Fernandez

## Descripcion del proyecto

Este proyecto contiene ejemplos practicos de los 5 principios SOLID en Java, con pruebas unitarias en JUnit 5 y construccion con Maven.

## Aclaracion sobre patrones de diseno

Para evitar confusion, este README separa:

- Patrones de diseno implementados: los que ya aparecen en el codigo actual.
- Patrones de diseno aplicables: los que se pueden implementar como mejora en los retos.

## Principios SOLID y patrones

### 1. SRP - Single Responsibility Principle

Implementacion actual:

- `Invoice`: datos y calculo total de factura.
- `InvoicePrinter`: impresion de factura.
- `InvoiceDatabaseSaver`: persistencia de factura.

Patrones implementados:

- Ningun patron GoF explicito.

Patrones aplicables en retos:

- `Facade`: unificar operaciones de factura en una sola interfaz.
- `Decorator`: agregar formatos o comportamientos extra de impresion.

### 2. OCP - Open/Closed Principle

Implementacion actual:

- `DiscountStrategy` (interfaz).
- `RegularCustomerDiscount` y `VipCustomerDiscount`.
- `DiscountCalculator` recibe cualquier estrategia.

Patrones implementados:

- `Strategy` (implementado): el calculo de descuento cambia segun la estrategia inyectada.

Patrones aplicables en retos:

- `Abstract Factory`: construir familias de descuentos por tipo de cliente.
- `Decorator`: combinar descuentos acumulables sin modificar clases existentes.

### 3. LSP - Liskov Substitution Principle

Implementacion actual:

- `Drivable` y `Refuelable` como contratos separados.
- `Car` implementa ambos.
- `ElectricCar` implementa solo `Drivable`.

Patrones implementados:

- Ningun patron GoF explicito.

Patrones aplicables en retos:

- `Adapter`: integrar vehiculos con interfaces heredadas.
- `Bridge`: separar tipo de propulsion y capacidad de conduccion.

### 4. ISP - Interface Segregation Principle

Implementacion actual:

- `Workable` y `Eatable` como interfaces pequenas.
- `Bot` implementa solo `Workable`.
- `Developer` implementa `Workable` y `Eatable`.

Patrones implementados:

- Ningun patron GoF explicito.

Patrones aplicables en retos:

- `Adapter`: adaptar clases antiguas a interfaces mas especificas.

### 5. DIP - Dependency Inversion Principle

Implementacion actual:

- `Database` (abstraccion).
- `MySQLDatabase` (implementacion concreta).
- `OrderProcessor` depende de `Database` via constructor.

Patrones implementados:

- `Dependency Injection` (implementado): `OrderProcessor` recibe la dependencia por constructor.

Patrones aplicables en retos:

- `Abstract Factory`: creacion de repositorios o proveedores de BD por entorno.
- `Repository`: encapsular acceso a datos de dominio.

## Tests unitarios

Ejecutar todos:

```bash
mvn clean test
```

Ejecutar por principio:

```bash
mvn test -Dtest=SRPTest
mvn test -Dtest=OCPTest
mvn test -Dtest=LSPTest
mvn test -Dtest=ISPTest
mvn test -Dtest=DIPTest
```

## Ejecutar ejemplos

1. En `pom.xml`, configurar la clase principal:

```xml
<configuration>
    <mainClass>com.example.solid.PRINCIPIO.GoodExample</mainClass>
</configuration>
```

2. Reemplazar `PRINCIPIO` por: `srp`, `ocp`, `lsp`, `isp` o `dip`.

3. Ejecutar:

```bash
mvn clean compile
mvn exec:java
```
