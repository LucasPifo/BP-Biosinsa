foreach(DBForm('Administración de Servicios')->WHERE('Bandera','=','1')->WHERE('Estatus','=','Activo')->WHERE('Fecha de ejecucion','=',currentDate())->WHERE('Servicio asociado','=','Monitoreo de insectos voladores Exteriores')->get() AS $stockRow){
    $Contador=0;
    foreach(DBForm('Nivel de Escalación')->WHERE('Idcon','=',$stockRow['idcont'])->WHERE('Nivel de Escalacion','=','1')->WHERE('Tipo de servicio','=','Monitoreo de insectos voladores Exteriores')->get() AS $Nivel){
        $Contador++;
    }
    foreach(DBForm('Areas del Servicio')->WHERE('IDPROG','=',$stockRow['ID Programación'])->get() AS $AreasDisp){
        foreach(DBForm('Asignacion de Dispositivos')->WHERE('IDAREA','=',$AreasDisp['IdArea'])->WHERE('Clasificacion Dispositivo','=','VE')->get() AS $Asignacion){
            $Administracion=NewRegister('Monitoreo de Dispositivos de Control Insectos Voladores Exteriores');
            $Administracion['No. Dispositivo']=$Asignacion['Numero de dispositivo QR'];
            $Administracion['Contrato']=$stockRow['Contrato'];
            $Administracion['Llave QR']=$stockRow['N.S.T'].$Asignacion['Numero de dispositivo QR'];
            $Administracion['IDASGD']=$Asignacion['Consecutivo'];
            $Administracion['Area']=$AreasDisp['Area'];
            $Administracion['N.S.T']=$stockRow['N.S.T'];
            $Administracion['Cliente']=$stockRow['Cliente'];
            $Administracion['Fecha']=$stockRow['Fecha de ejecucion'];
            $Administracion['Tecnico que firma']=$AreasDisp['Tecnico'];
            $Administracion['Personal Encargado']=$stockRow['Personal Encargado'];
            $Administracion['Tipo de servicio']=$stockRow['Servicio asociado'];
            $Administracion['Servicio']=$stockRow['Servicio'];
            $Administracion['Programa']=$stockRow['Programa'];
            $Administracion['Consumible']=$Asignacion['Consumible 1'];
            $Administracion['Contacto']=$stockRow['Contacto'];
            $numerito=$Asignacion['Numero de dispositivo QR'];
            $res=explode( '-', $numerito ) ;	
            $resultado=array_pop($res);
            $Administracion['Numerodispositivoabre']="VE-".$resultado;
            save($Administracion);
        }
    }
    $stockRow['Estatus']='Activo';
    $stockRow['Bandera']='0';
    save($stockRow);
}