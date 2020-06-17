//adiciona mascara a moeda
function formatarMoeda(valor, casas, separdor_decimal, separador_milhar) {

    var valor_total = parseInt(valor * (Math.pow(10, casas)));
    var inteiros = parseInt(parseInt(valor * (Math.pow(10, casas))) / parseFloat(Math.pow(10, casas)));
    var centavos = parseInt(parseInt(valor * (Math.pow(10, casas))) % parseFloat(Math.pow(10, casas)));


    if (centavos % 10 == 0 && centavos + "".length < 2) {
        centavos = centavos + "0";
    } else if (centavos < 10) {
        centavos = "0" + centavos;
    }

    var milhares = parseInt(inteiros / 1000);
    inteiros = inteiros % 1000;

    var retorno = "";

    if (milhares > 0) {
        retorno = milhares + "" + separador_milhar + "" + retorno
        if (inteiros == 0) {
            inteiros = "000";
        } else if (inteiros < 10) {
            inteiros = "00" + inteiros;
        } else if (inteiros < 100) {
            inteiros = "0" + inteiros;
        }
    }
    retorno += inteiros + "" + separdor_decimal + "" + centavos;


    return retorno;

}


//adiciona mascara ao telefone
function MascaraTelefone(tel, evento) {
    var vr = tel.value.length;

    if (vr > 13) {
        if (mascaraInteiro(tel) == false) {
            event.returnValue = false;
        }
        if (document.all) {
            return formataCampo(tel, '(00) 0000-0000', window.event);
        } else {
            return formataCampo(tel, '(00) 0000-0000', evento);
        }
    } else {
        if (mascaraInteiro(tel) == false) {
            event.returnValue = false;
        }
        if (document.all) {
            return formataCampo(tel, '(00) 000-0000', window.event);
        } else {
            return formataCampo(tel, '(00) 000-0000', evento);
        }
    }
}

//adiciona mascara de placa
function MascaraPlaca(placa, evento) {
    if (mascaraInteiro(placa) == false) {
        event.returnValue = false;
    }
    if (document.all) {
        return formataCampo(placa, '000-0000', event);
    } else {
        return formataCampo(placa, '000-0000', evento);
    }
}

//adiciona mascara de cep
function MascaraCep(cep, evento) {
    if (mascaraInteiro(cep) == false) {
        event.returnValue = false;
    }
    if (document.all) {
        return formataCampo(cep, '00.000-000', event);
    } else {
        return formataCampo(cep, '00.000-000', evento);
    }
}

//adiciona mascara ao CPF
function MascaraCPF(cpf, evento) {
    if (mascaraInteiro(cpf) == false) {
        event.returnValue = false;
    }
    if (document.all) {
        return formataCampo(cpf, '000.000.000-00', event);
    } else {
        return formataCampo(cpf, '000.000.000-00', evento);
    }
}

//adiciona mascara de cnpj
function MascaraCNPJ(cnpj, evento) {
    if (mascaraInteiro(cnpj) == false) {
        event.returnValue = false;
    }
    if (document.all) {
        return formataCampo(cnpj, '00.000.000/0000-00', event);
    } else {
        return formataCampo(cnpj, '00.000.000/0000-00', evento);
    }
}

//adiciona mascara ao CPF ou CNPJ
function MascaraCPFCNPJ(campo, evento) {

    var vr = campo.value.length;

    if (vr > 14) {
        if (mascaraInteiro(campo) == false) {
            event.returnValue = false;
        }
        if (document.all) {
            return formataCampo(campo, '00.000.000/0000-00', event);
        } else {
            return formataCampo(campo, '00.000.000/0000-00', evento);
        }
    } else {
        if (mascaraInteiro(campo) == false) {
            event.returnValue = false;
        }
        if (document.all) {
            return formataCampo(campo, '000.000.000-00', event);
        } else {
            return formataCampo(campo, '000.000.000-00', evento);
        }
    }
}

//valida numero inteiro com mascara
function mascaraInteiro(evento) {
    var code;
    if (document.all) {
        code = window.event.keyCode;
    } else {
        code = evento.which;
    }
    if ((code < 48 || code > 57)
            && (code != 13) && (code != 8) && (code != 9))
    {
        code = 0;
        return false;
    }
    return true;
}

function validar(obj)
{
    if (obj.value == "")
        return;

    var regExp = /(^-*\d+$)|(^-*\d+\,\d+$)|(^-*\d+\.\d+\,\d+$)/;
    if (!regExp.test(obj.value))
    {
        obj.select();
        alert('Informe apenas números.');
        return;
    }
}

//valida Email
function checkMail(mail) {
    var filter = /^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i;
    if (filter.test(mail.value))
        return;
    if (mail.value == "")
        return;
    else {
        mail.select();
        alert("Este endereço de e-mail não é válido!");
    }
}

//valida CEP
function ValidaCep(cep) {
    if (cep.value == "")
        return;
    exp = /^[0-9]{2}\.[0-9]{3}-[0-9]{3}$/;
    if (!exp.test(cep.value)) {
        cep.select();
        alert('Número de cep inválido!');
    }
}

//valida telefone
function ValidaTelefone(tel) {
    var vr = tel.value.length;
    if (tel.value == "")
        return;
    if (vr > 13) {
        exp = /^(\(?[0-9]{2}\)?|[-. ]?)[ ][0-9]{4}[-. ]?[0-9]{4}$/
        if (!exp.test(tel.value)) {
            tel.select();
            alert('Número de telefone inválido!');
        }
    } else {
        exp = /^(\(?[0-9]{2}\)?|[-. ]?)[ ][0-9]{3}[-. ]?[0-9]{4}$/
        if (!exp.test(tel.value)) {
            tel.select();
            alert('Número de telefone inválido!');
        }
    }

}

//valida a placa digitada
function ValidarPlaca(placa) {
    if (placa.value == "")
        return;
    exp = /^([A-Z]|[a-z]){3}\-[0-9]{4}$/;
    if (!exp.test(placa.value)) {
        placa.select();
        alert('Formato de placa inválido!');
    }
}

function remove(str, sub) {
    i = str.indexOf(sub);
    r = "";
    if (i == -1)
        return str;
    r += str.substring(0, i) + remove(str.substring(i + sub.length), sub);
    return r;
}

//valida o CPF digitado
function ValidarCPF(Objcpf) {

    var cpf = Objcpf.value;

    if (cpf == "")
        return;

    var regExp = /(^-*\d{3}\.\d{3}\.\d{3}\-\d{2}$)/;
    if (!regExp.test(cpf)) {
        //alert('Informe apenas números.');
        Objcpf.value = "";
        Objcpf.focus();
        return;
    }

    cpf = remove(cpf, ".");
    cpf = remove(cpf, "-");

    if (cpf == "00000000000" || cpf == "11111111111" ||
            cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" ||
            cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" ||
            cpf == "88888888888" || cpf == "99999999999") {
        //alert("CPF inválido.");
        Objcpf.value = "";
        Objcpf.focus();
        return false;
    }

    soma = 0;
    for (i = 0; i < 9; i++)
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    resto = 11 - (soma % 11);
    if (resto == 10 || resto == 11)
        resto = 0;
    if (resto != parseInt(cpf.charAt(9))) {
        //alert("CPF inválido.");
        Objcpf.value = "";
        Objcpf.focus();
        return false;
    }
    soma = 0;
    for (i = 0; i < 10; i++)
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    resto = 11 - (soma % 11);
    if (resto == 10 || resto == 11)
        resto = 0;
    if (resto != parseInt(cpf.charAt(10))) {
        alert("CPF inválido.");
        Objcpf.value = "";
        Objcpf.focus();
        return false;
    }
    return true;
}

//valida o CNPJ digitado
function ValidarCNPJ(ObjCnpj) {

    if (ObjCnpj.value == "")
        return;

    var cnpj = ObjCnpj.value;
    var valida = new Array(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2);
    var dig1 = new Number;
    var dig2 = new Number;

    exp = /\.|\-|\//g
    cnpj = cnpj.toString().replace(exp, "");
    var digito = new Number(eval(cnpj.charAt(12) + cnpj.charAt(13)));

    for (i = 0; i < valida.length; i++) {
        dig1 += (i > 0 ? (cnpj.charAt(i - 1) * valida[i]) : 0);
        dig2 += cnpj.charAt(i) * valida[i];
    }
    dig1 = (((dig1 % 11) < 2) ? 0 : (11 - (dig1 % 11)));
    dig2 = (((dig2 % 11) < 2) ? 0 : (11 - (dig2 % 11)));

    if (((dig1 * 10) + dig2) != digito) {
        //alert('Número de CNPJ inválido!');
        ObjCnpj.value = "";
        ObjCnpj.focus();
    }
}

function ValidarCPFCNPJ(campo) {
    var vr = campo.value.length;

    if (vr > 14) {
        ValidarCNPJ(campo);
    } else {
        ValidarCPF(campo);
    }
}

//formata de forma generica os campos
function formataCampo(campo, Mascara, evento) {
    var boleanoMascara;

    var Digitato;
    if (document.all) {
        Digitato = window.event.keyCode;
    } else {
        Digitato = evento.which;
    }

    exp = /\-|\.|\/|\(|\)| /g
    campoSoNumeros = campo.value.toString().replace(exp, "");

    var posicaoCampo = 0;
    var NovoValorCampo = "";
    var TamanhoMascara = campoSoNumeros.length;
    ;

    if (Digitato != 8) { // backspace
        for (i = 0; i <= TamanhoMascara; i++) {
            boleanoMascara = ((Mascara.charAt(i) == "-") || (Mascara.charAt(i) == ".")
                    || (Mascara.charAt(i) == "/"))
            boleanoMascara = boleanoMascara || ((Mascara.charAt(i) == "(")
                    || (Mascara.charAt(i) == ")") || (Mascara.charAt(i) == " "))
            if (boleanoMascara) {
                NovoValorCampo += Mascara.charAt(i);
                TamanhoMascara++;
            } else {
                NovoValorCampo += campoSoNumeros.charAt(posicaoCampo);
                posicaoCampo++;
            }
        }
        campo.value = NovoValorCampo;
        return true;
    } else {
        return true;
    }
}