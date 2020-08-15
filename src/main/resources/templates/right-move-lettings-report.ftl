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

    <h3> Number of let properties:  ${numberOfLetProperties} </h3>
    <h3> Number of new properties on market:  ${numberOfNewProperties} </h3>

    <#if letProperties?size != 0>

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
                    Type
                </th>
                <th>
                    Number of Beds
                </th>
                <th>
                    Days to Let
                </th>
                <th>
                    Is Premium
                </th>
            </tr>
            <#list letProperties as letProperty>
            <tr>
                <td>
                    <a href="${letProperty.fullPropertyUrl}">
                        <img src="${letProperty.mainMapImageSrc}" alt="image" >
                    </a>
                </td>
                <td>
                    <p>${letProperty.displayAddress}
                </td>
                <td>
                    <p>${letProperty.propertySubType}
                </td>
                <td>
                    <p>${letProperty.bedrooms}
                </td>
                <td>
                    <p>${letProperty.daysOnMarket()}
                </td>
                <td>
                    <p><#if letProperty.premiumListing??>${letProperty.premiumListing?string('Yes', 'No')}<#else>No</#if>
                </td>
            </tr>
            </#list>
        </table>
    </#if>

    <#if newProperties?size != 0>
        <h3>Recently added Properties:</h3>
        <table border="true" style="text-align: center">
        <tr>
            <th>
                Property
            </th>
            <th>
                Address
            </th>
            <th>
                Type
            </th>
            <th>
                Number of Beds
            </th>
            <th>
                Days to Let
            </th>
            <th>
                Is Premium
            </th>
        </tr>
        <#list newProperties as newProperty>
            <tr>
                <td>
                    <a href="${newProperty.fullPropertyUrl}">
                        <img src="${newProperty.mainMapImageSrc}" alt="image" >
                    </a>
                </td>
                <td>
                    <p>${newProperty.displayAddress}
                </td>
                <td>
                    <p>${newProperty.propertySubType}
                </td>
                <td>
                    <p>${newProperty.bedrooms}
                </td>
                <td>
                    <p>${newProperty.daysOnMarket()}
                </td>
                <td>
                    <p><#if newProperty.premiumListing??>${newProperty.premiumListing?string('Yes', 'No')}<#else>No</#if>
                </td>
            </tr>
        </#list>
    </table>
    </#if>

</body>
</html>
