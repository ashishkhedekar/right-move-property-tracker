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

<h3> Number of properties off-market:  ${numberOfOffMarketProperties} </h3>

<#if offMarketProperties?size != 0>

    <h3>Recently off-market Properties:</h3>
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
                    <p><#if offMarketProperties.premiumListing??>${offMarketProperties.premiumListing?string('Yes', 'No')}<#else>No</#if>
                </td>
            </tr>
        </#list>
    </table>
</#if>

</body>
</html>
