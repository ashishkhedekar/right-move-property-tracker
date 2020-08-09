<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="/css/bootstrap.css" rel="stylesheet" />
</head>
<body class="bg-light">
<h1>Today's summary!</h1>

    <h3> Number of properties let:  ${numberOfPropertiesLet} </h3>

    <h3> Number of properties sold: ${newProperties}</h3>

    <h3>Recently Let Properties:</h3>
    <table border="true" style="text-align: center">
        <tr>
            <th>
                Property
            </th>
            <th>
                Address
            </th>
            <th>
                Summary
            </th>
            <th>
                Type
            </th>
            <th>
                Number of Beds
            </th>
        </tr>
        <#list letProperties as letProperty>
        <tr>
            <td>
                <a href="${letProperty.fullPropertyUrl}">
                    <img src="${letProperty.propertyImages.mainMapImageSrc}" alt="image" >
                </a>
            </td>
            <td>
                <p>${letProperty.displayAddress}
            </td>
            <td>
                <p>${letProperty.summary}
            </td>
            <td>
                <p>${letProperty.propertySubType}
            </td>
            <td>
                <p>${letProperty.bedrooms}
            </td>
        </tr>
        </#list>
    </table>
    </div>
</body>
</html>
