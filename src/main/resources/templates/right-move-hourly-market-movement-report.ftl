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

    <#if offMarketProperties?size != 0>
        <h3> Number of properties ${changeOfStatus}:  ${numberOfOffMarketProperties} </h3>
    </#if>

    <#if newProperties?size != 0>
        <h3> Number of new properties on market:  ${numberOfNewProperties} </h3>
    </#if>

    ========================================================================================

    <#if offMarketProperties?size != 0>

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
                    Price
                </th>
            </tr>
            <#list offMarketProperties as offMarketProperties>
            <tr>
                <td>
                    <a href="${offMarketProperties.fullPropertyUrl}">
                        <img src="${offMarketProperties.mainMapImageSrc}" alt="image" >
                    </a>
                </td>
                <td>
                    <p>${offMarketProperties.displayAddress}
                </td>
                <td>
                    <p>${offMarketProperties.propertySubType}
                </td>
                <td>
                    <p>${offMarketProperties.bedrooms}
                </td>
                <td>
                    <p>${offMarketProperties.daysOnMarket()}
                </td>
                <td>
                    <p>${offMarketProperties.displayPrice}
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
                Price
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
                    <p>${newProperty.displayPrice}
                </td>
            </tr>
        </#list>
    </table>
    </#if>

</body>
</html>
