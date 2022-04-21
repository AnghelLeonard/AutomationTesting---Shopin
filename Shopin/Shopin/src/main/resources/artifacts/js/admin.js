function trimiteCod() {
    var sku = document.getElementById("skuId").value;

    if (sku.length !== 10) {
        document.getElementById("errorSkuId").innerHTML = "<strong>Eroare (Cod produs): Codul produsului trebuie sa contina 10 caractere!</strong>";
        alert('Eroare (Cod produs): Codul produsului trebuie sa contina 10 caractere!');
        return;
    }

    var nowhitespaces = /^[a-zA-Z0-9]*$/;
    if (!sku.match(nowhitespaces)) {
        document.getElementById("errorSkuId").innerHTML = "<strong>Eroare (Cod produs): Singurele caractere permise sunt cifrele si literele!</strong>";
        alert('Eroare (Cod produs): Singurele caractere permise sunt cifrele si literele!');
        return;
    }

    document.getElementById("hrefSkuId").setAttribute("href", "/x10qerK0/modify?sku=" + sku);
    document.getElementById("hrefSkuId").click();
}

function validareAdaugaProdus() {

    document.getElementById("descriereCompletaId").value = "";

    // validare nume
    var v = document.getElementById("numeId").value;

    document.getElementById("errorId").innerHTML = "";

    if (v.length < 5) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Nume): Numele trebuie sa contina minim 5 caractere!!</strong>";
        alert('Eroare (Nume): Numele trebuie sa contina minim 5 caractere!!');
        return;
    }

    if (v.length > 100) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Nume): Ati depasit limita de 100 de caractere cu " + (v.length - 100) + " de caractere!</strong>";
        alert('Eroare (Nume): Ati depasit limita de 100 de caractere!</strong>');
        return;
    }

    var letterNumber = /^[a-zA-Z0-9\s-_]*$/;
    if (!v.match(letterNumber)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Nume): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele si spatiile!</strong>";
        alert('Eroare (Nume): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele si spatiile!');
        return;
    }

    var c = document.getElementById("codId").value;

    if (c.length !== 10) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Cod produs): Codul produsului trebuie sa contina 10 caractere!</strong>";
        alert('Eroare (Cod produs): Codul produsului trebuie sa contina 10 caractere!');
        return;
    }

    var nowhitespaces = /^[a-zA-Z0-9]*$/;
    if (!c.match(nowhitespaces)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Cod produs): Singurele caractere permise sunt cifrele si literele!</strong>";
        alert('Eroare (Cod produs): Singurele caractere permise sunt cifrele si literele!');
        return;
    }

    var p = document.getElementById("pretId").value;

    if (p.length < 2) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Modifica pret): Pretul produsului trebuie sa contina minim 2 caractere!!</strong>";
        alert('Eroare (Modifica pret): Pretul produsului trebuie sa contina minim 2 caractere!!');
        return;
    }

    if (p !== parseFloat(p).toFixed(2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Modifica pret): Pretul produsului trebuie sa contina 2 zecimale!!</strong>";
        alert('Eroare (Modifica pret): Pretul produsului trebuie sa contina 2 zecimale!!');
        return;
    }

    //validare descriere
    var descriere1 = document.getElementById("descId1").value;
    var descriere2 = document.getElementById("descId2").value;
    var descriere3 = document.getElementById("descId3").value;
    var descriere4 = document.getElementById("descId4").value;
    var descriere5 = document.getElementById("descId5").value;
    var descriere6 = document.getElementById("descId6").value;
    var compozitie1 = document.getElementById("compozitieId1").value;
    var compozitie2 = document.getElementById("compozitieId2").value;

    document.getElementById("errorId").innerHTML = "";

    var letterNumber2 = /^^[a-zA-Z0-9\s%;,:]*$/;

    if (descriere1.length < 10) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - primul camp): Descrierea produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Descriere - primul camp): Descrierea produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (descriere1.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - primul camp): Descrierea produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Descriere - primul camp): Descrierea produsului a depasit 800 de caractere!!');
        return;
    }

    if (!descriere1.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - primul camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Descriere - primul camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;
    }

    if (descriere2.length < 10) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al doilea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Descriere - al doilea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (descriere2.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al doilea camp): Descrierea produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Descriere - al doilea camp): Descrierea produsului a depasit 800 de caractere!!');
        return;
    }

    if (!descriere2.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al doilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Descriere - al doilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;
    }

    if (descriere3.length < 10) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al treilea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Descriere - al treilea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (descriere3.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al treilea camp): Descrierea produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Descriere - al treilea camp): Descrierea produsului a depasit 800 de caractere!!');
        return;
    }

    if (!descriere3.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al treilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Descriere - al treilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;

    }

    if (descriere4.length < 10 && descriere4.length > 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al patrulea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Descriere - al patrulea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (descriere4.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al patrulea camp): Descrierea produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Descriere - al patrulea camp): Descrierea produsului a depasit 800 de caractere!!');
        return;
    }

    if (!descriere4.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al patrulea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Descriere - al patrulea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;
    }

    if (descriere5.length < 10 && descriere5.length > 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al cincilea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Descriere - al cincilea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (descriere5.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al cincilea camp): Descrierea produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Descriere - al cincilea camp): Descrierea produsului a depasit 800 de caractere!!');
        return;
    }

    if (!descriere5.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al cincilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Descriere - al cincilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;
    }

    if (descriere6.length < 10 && descriere6.length > 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al saselea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Descriere - al saselea camp): Descrierea produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (descriere6.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al saselea camp): Descrierea produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Descriere - al saselea camp): Descrierea produsului a depasit 800 de caractere!!');
        return;
    }

    if (!descriere6.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al saselea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Descriere - al saselea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;
    }

    if (descriere5.length > 0 && descriere4.length === 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al patrulea camp): Completati campul al patrulea inainte de al cincilea!!</strong>";
        alert('Eroare (Descriere - al patrulea camp): Completati campul al patrulea inainte de al cincilea!!');
        return;
    }

    if (descriere6.length > 0 && descriere5.length === 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al cincilea camp): Completati campul al cincilea inainte de al saselea!!</strong>";
        alert('Eroare (Descriere - al cincilea camp): Completati campul al cincilea inainte de al saselea!!');
        return;
    }

    if (descriere6.length > 0 && descriere4.length === 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Descriere - al patrulea camp): Completati campul al patrulea inainte de al saselea!!</strong>";
        alert('Eroare (Descriere - al patrulea camp): Completati campul al patrulea inainte de al saselea!!');
        return;
    }

    if (compozitie1.length < 10) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Compozitie - primul camp): Descrierea compozitiei produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Compozitie - primul camp): Descrierea compozitiei produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (compozitie1.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Compozitie - primul camp): Descrierea compozitiei produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Compozitie - primul camp): Descrierea compozitiei produsului a depasit 800 de caractere!!');
        return;
    }

    if (!compozitie1.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Compozitie - primul camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Compozitie - primul camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;
    }

    if (compozitie2.length > 800) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Compozitie - al doilea camp): Descrierea compozitiei produsului a depasit 800 de caractere!!</strong>";
        alert('Eroare (Compozitie - al doilea camp): Descrierea compozitiei produsului a depasit 800 de caractere!!');
        return;
    }

    if (!compozitie2.match(letterNumber2)) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Compozitie - al doilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!</strong>";
        alert('Eroare (Compozitie - al doilea camp): Ati introdus caractere nepermise! Singurele caractere permise sunt cifrele, caracterele, spatiile, virugula, procent si punct si virgula!');
        return;
    }

    if (compozitie2.length > 0 && compozitie2.length < 10) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Compozitie - al doilea camp): Descrierea compozitiei produsului trebuie sa contina minim 10 caractere!!</strong>";
        alert('Eroare (Compozitie - al doilea camp): Descrierea compozitiei produsului trebuie sa contina minim 10 caractere!!');
        return;
    }

    if (compozitie2.length > 0 && compozitie1.length === 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Compozitie - primul camp): Completati primul camp inainte de al doilea!!</strong>";
        alert('Eroare (Compozitie - primul camp): Completati primul camp inainte de al doilea!!');
        return;
    }

    var descriereFinala = "@Descriere produs@ " + "#" + descriere1 + "#" + descriere2 + "#" + descriere3;
    descmsg = descriere1 + ", " + descriere2 + ", " + descriere3;

    if (descriere4 !== "") {
        descriereFinala = descriereFinala + "#" + descriere4;
        descmsg = descmsg + ", " + descriere4;
    }

    if (descriere5 !== "") {
        descriereFinala = descriereFinala + "#" + descriere5;
        descmsg = descmsg + ", " + descriere5;
    }

    if (descriere6 !== "") {
        descriereFinala = descriereFinala + "#" + descriere6;
        descmsg = descmsg + ", " + descriere6;
    }

    descriereFinala = descriereFinala + "@Compozitie@ " + "#" + compozitie1;
    compmsg = compozitie1;

    if (compozitie2 !== "") {
        descriereFinala = descriereFinala + "#" + compozitie2;
        compmsg = compmsg + ", " + compozitie2;
    }

    document.getElementById("descriereCompletaId").value = descriereFinala;

    var arethey = document.getElementById("marimiId").value;
    if (arethey.length === 0) {
        // <![CDATA[ 
        for (var i = 1; i <= 6; i++) {
            colectSizes(i, true);
            if (document.getElementById("marimiId").value.length > 0) {
                break;
            }
        }
        // ]]>    
    }

    if (document.getElementById("marimiId").value.length === 0) {
        document.getElementById("errorId").innerHTML = "<strong>Eroare (Masuri): Nu a-ti selectat masurile posibile!!</strong>";
        alert('Eroare (Masuri): Nu a-ti selectat masurile posibile!!');
        return;
    }

    calculProdus();
}

function showSizes() {
    var v1 = document.getElementById("marime1Id").checked;
    var v2 = document.getElementById("marime2Id").checked;
    var v3 = document.getElementById("marime3Id").checked;
    var v4 = document.getElementById("marime4Id").checked;
    var v5 = document.getElementById("marime5Id").checked;
    var v6 = document.getElementById("marime6Id").checked;

    document.getElementById("m1").style.display = "none";
    document.getElementById("m2").style.display = "none";
    document.getElementById("m3").style.display = "none";
    document.getElementById("m4").style.display = "none";
    document.getElementById("m5").style.display = "none";
    document.getElementById("m6").style.display = "none";

    if (v1)
    {
        document.getElementById("m1").style.display = "block";
    }

    if (v2)
    {
        document.getElementById("m2").style.display = "block";
    }

    if (v3)
    {
        document.getElementById("m3").style.display = "block";
    }

    if (v4)
    {
        document.getElementById("m4").style.display = "block";
    }

    if (v5)
    {
        document.getElementById("m5").style.display = "block";
    }

    if (v6)
    {
        document.getElementById("m6").style.display = "block";
    }
}

function colectSizes(bloc, all) {

    document.getElementById("marimiId").value = "";

    var contor = 1;
    var finalv = "";
    var flag = 0;
    formsg = "";
    while (document.getElementById("id" + contor + "-" + bloc)) {

        var v = document.getElementById("id" + contor + "-" + bloc).parentNode.textContent;

        if (document.getElementById("id" + contor + "-" + bloc).checked) {
            if (flag === 0) {
                flag = 1;
                finalv = finalv + "_";
            }
            finalv = finalv + v + "-" + bloc + "_";
            formsg = formsg + v + "   ";
        }
        contor++;
    }

    if (!all) {
        if (finalv.length < 5) {
            document.getElementById("errorId").innerHTML = "<strong>Eroare (Marimi): Nu ati selectat nici o marime!</strong>";
            alert('Eroare (Marimi): Nu ati selectat nici o marime!');
            return;
        }
    }
    document.getElementById("marimiId").value = finalv;
}

function calculProdus() {

    var p = "{\"product\":{";

    p = p + "\"name\":\"" + document.getElementById("numeId").value + "\","; //nume
    p = p + "\"sku\":\"" + document.getElementById("codId").value + "\","; //cod produs
    p = p + "\"price\":\"" + document.getElementById("pretId").value + "\","; //pret
    var i = document.getElementById("categoryId").options[document.getElementById("categoryId").selectedIndex].value;
    p = p + "\"categoryid\":\"" + i.substring(0, i.indexOf("|")) + "\","; //categorie id
    p = p + "\"mcat\":\"" + i.substring(i.indexOf("|/") + 2, i.indexOf("|/") + 3) + "\","; // categorie principala
    p = p + "\"scat\":\"" + i.substring(i.indexOf("|/") + 4, i.indexOf("|/") + 6) + "\","; // secundara
    p = p + "\"discount\":\"" + document.getElementById("discountId").options[document.getElementById("discountId").selectedIndex].value + "\","; //discount
    p = p + "\"firstpage\":\"" + document.getElementById("firstpageId").checked + "\","; //prima pagina
    p = p + "\"live\":\"" + document.getElementById("liveId").checked + "\","; //live
    var culori = document.getElementsByName("culoare");
    for (var i = 0; i < culori.length; i++) {
        if (culori[i].checked === true) {
            p = p + "\"color\":\"" + culori[i].value + "\","; //culoarea
            culoareatext = culori[i].parentNode.textContent;
            break;
        }
    }
    p = p + "\"sizes\":\"" + document.getElementById("marimiId").value + "\","; //marimi
    p = p + "\"description\":\"" + document.getElementById("descriereCompletaId").value + "\""; //descriere

    p = p + "}}";

//    p = '{"product":{"name":"Gehete cochete scurti","sku":"PPYWQB9WWQ","price":"3347.27","categoryid":"14","mcat":"f","scat":"in","discount":"11","firstpage":"false","live":"true","color":"b","sizes":"_U-3_XXL/B-3_XXS-3_XXL-3_XXL-3XL-3_3XL-3_","description":"@Descriere produs@ #11111111111111111111111111111111#222222222222222222222222222222#333333333333333333333333333333#344444444444444444444444#4455555555555555555555555555#5666666666666666666666666@Compozitie@ #6777777777777777777777777777777#7788888888888888888888888888888"}}';

    // alert(p);

    bootbox.confirm({
        title: "Produs",
        message: "Detaliile produsului sunt listate mai jos. Confirmati salvarea? <br/><br/>" +
                "<strong>Nume</strong>: " + document.getElementById("numeId").value + "<br/>" +
                "<strong>Cod produs:</strong>: " + document.getElementById("codId").value + "<br/>" +
                "<strong>Pret:</strong>: " + document.getElementById("pretId").value + "<br/>" +
                "<strong>Categorie:</strong>: " + document.getElementById("categoryId").options[document.getElementById("categoryId").selectedIndex].textContent + "<br/>" +
                "<strong>Discount:</strong>: " + document.getElementById("discountId").options[document.getElementById("discountId").selectedIndex].textContent + "<br/>" +
                "<strong>Prima pagina:</strong>: " + (document.getElementById("firstpageId").checked ? "da" : "nu") + "<br/>" +
                "<strong>Produs activ (live):</strong>: " + (document.getElementById("liveId").checked ? "da" : "nu") + "<br/>" +
                "<strong>Culoarea:</strong>: " + culoareatext + "<br/>" +
                "<strong>Marimi:</strong>: " + formsg + "<br/>" +
                "<strong>Descriere:</strong><br/>" + descmsg + "<br/>" +
                "<strong>Compozitie:</strong><br/>" + compmsg + "<br/>",
        buttons: {
            confirm: {
                label: 'Da',
                className: 'btn-success'
            },
            cancel: {
                label: 'Nu',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {
                document.getElementById("successId").value = "ASTEAPTA";
                document.getElementById("productFinalId").value = p;
                document.forms["addProductFormId"].submit();
            }
        }
    });
}