<!DOCTYPE html>

<html lang="en">
<head>
    <title>Inventory System</title>
    <link rel='stylesheet' href='/webjars/bootstrap/3.3.5/css/bootstrap.min.css'>
</head>
<body>
<h2>Product List</h2>
<table>
    <thead>
    <tr>
        <td>Product Name</td>
        <td>Stock</td>
        <td>Price</td>
        <td>Company</td>
        <td>Action</td>
    </tr>
    </thead>
    <tbody>
    <#list inventories as inventory>
    <tr>
        <td>${inventory.productName}</td>
        <td>${inventory.stock}</td>
        <td>${inventory.price}</td>
        <td>${inventory.company}</td>
        <td><a href="editStock/${inventory.id}"><button type="button">update stock</button></a></td>
        <td><a href="editPrice/${inventory.id}"><button type="button">update price</button></a></td>
        <td><a href="delete/${inventory.id}"><button type="button">delete</button></a></td>
    </tr>
    </#list>
    <tr>
        <td colspan="4" align="right"><a href="create"><button type="button">add</button></a></td>
    </tr>
    </tbody>
</table>
</body>
</html>
