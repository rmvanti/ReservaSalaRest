$(document).ready(function(){
			
    //definição das URL's dos serviços
    var urlToInsert  = 'http://localhost:8084/ReservaSalaREST/rest/reserve';				
    var urlToGetList = 'http://localhost:8084/ReservaSalaREST/rest/reserve';
    var urlToGetById = 'http://localhost:8084/ReservaSalaREST/rest/reserve/{id}';
    var urlToDelete  = 'http://localhost:8084/ReservaSalaREST/rest/reserve/delete/{id}';
    var urlToUpdate  = 'http://localhost:8084/ReservaSalaREST/rest/reserve/edit/{id}';
    var urlToGetPlaceList = 'http://localhost:8084/ReservaSalaREST/rest/place';
    var urlToGetRoomList = 'http://localhost:8084/ReservaSalaREST/rest/meetingRoom/{placeId}';

    var invocation = null;

    var btnOpenModal = $('.btnOpenModal');
    var btnDeleteList = $('.btnDeleteList');
    var btnEdit = $('.btnEdit');
    var btnDelete = $('.btnDelete');

    var modal = $('#reserveCrudModal');
    var modalReserveForm = modal.find('#reserveForm');

    var tbody = $('table').find('tBody');

    function getRoomsData(placeId){                    
        var field = $('.meetingRoomField');
        var invocation = new XMLHttpRequest();
        var url = urlToGetRoomList.replace('{placeId}', placeId)
        invocation.open('GET', url, true);
        invocation.withCredentials = false;
        invocation.onreadystatechange  = function(){
            if(invocation.readyState === 4 && invocation.status === 200){
                var json = invocation.response;
                var line = '';
                var first = true;
                json = typeof json === 'string' ? JSON.parse(json) : json;                               
                for(var i = 0; i < json.length; i++){
                    if(first){
                        line = '<option value="' + json[i].id  + '" selected >' + json[i].name +'</option>';
                        first = false;

                    }
                    line = '<option value="' + json[i].id  + '">' + json[i].name +'</option>';
                    field.append(line);
                }
            }
        },
        invocation.send();                       
    }

    function getPlacesData(){
        var field = $('.placeIdField');
        var invocation = new XMLHttpRequest();
        invocation.open('GET', urlToGetPlaceList, true);
        invocation.withCredentials = false;
        invocation.onreadystatechange  = function(){
            if(invocation.readyState === 4 && invocation.status === 200){
                var json = invocation.response;
                var line = '';
                var first = true;
                json = typeof json === 'string' ? JSON.parse(json) : json;                    
                for(var i = 0; i < json.length; i++){
                    if(first){
                        line = '<option value="' + json[i].id  + '">' + json[i].name +'</option>';                                                        
                        getRoomsData(json[i].id);
                        first = false;
                    }
                    line = '<option value="' + json[i].id  + '" >' + json[i].name +'</option>';
                    field.append(line);
                }
            }                
        },
        invocation.send();                       
    }

    function getGridData(){
        var invocation = new XMLHttpRequest();
        invocation.open('GET', urlToGetList, true);
        invocation.withCredentials = false;
        invocation.onreadystatechange  = function(){
            if(invocation.readyState === 4 && invocation.status === 200){
                var json = invocation.response;
                var line = '';
                json = typeof json === 'string' ? JSON.parse(json) : json;
                $('table tbody tr').remove();
                for(var i = 0; i < json.length; i++){
                    line = '<tr data-id="'+json[i].id+'">';
                    line += '<td><input type="hidden" value="'+json[i].id+'"></input></td>';
                    line += '<td>';
                    line += '<input type="checkbox" value="false" data-id="'+json[i].id+'"/>';
                    line += '</td>';
                    line += '<td><span>'+json[i].requester+'</span></td>';
                    line += '<td><span>'+json[i].meetingRoom.name+'</span></td>';
                    line += json[i].meetingRoom.multimediaResources === true ? '<td><span>Sim</span></td>' : '<td><span>Não</span></td>';
                    line += '<td><span>'+json[i].meetingRoom.capacity+'</span></td>';				
                    line += '<td><span>'+json[i].startDate+'</span></td>';
                    line += '<td><span>'+json[i].endDate+'</span></td>';
                    line += json[i].withCoffeeBreak === true ? '<td><span>Sim</span></td>' : '<td><span>Não</span></td>';
                    line += '<td><span>'+json[i].numberOfPeople+'</span></td>';
                    line += '<td>';
                    line += '<form>';
                    line += '<button class="btn btn-warning btn-xs btnEdit" data-id="'+json[i].id+'">Editar</button>';
                    line += '<button class="btn btn-danger btn-xs btnDelete" data-id="'+json[i].id+'">Remover</button>';
                    line += '</form>';
                    line += '</td>';
                    line += '</tr>';
                    $('table tbody').append(line);
                }
            }
        },
        invocation.send();
    };        				

    function clearModalFields(){
        //modal.find(".placeIdField").val('');
        //modal.find(".meetingRoomField").val('');        
        modal.find(".reserveIdField").val('');
        modal.find(".requesterField").val('');
        modal.find(".startDateField").val('');
        modal.find(".endDateField").val('');
        modal.find(".coffeeField").prop('checked', false);
        modal.find(".numberOfPeopleField").val('');
        modal.find(".numberOfPeopleElement").hide();
    };
    
    function checkModalFields(){
        return modal.find(".requesterField").val() && modal.find(".startDateField").val() && modal.find(".endDateField").val();
    }

    function modalFormToJson(){					
        return JSON.stringify({
            "id": modalReserveForm.find('.reserveIdField').val() === "" ? null : modalReserveForm.find('.reserveId').val(), 
            "placeId": modalReserveForm.find('.placeIdField').val(), 
            "meetingRoomId": modalReserveForm.find('.meetingRoomField').val(),
            "requester": modalReserveForm.find('.requesterField').val(),
            "startDate": modalReserveForm.find('.startDateField').val(),
            "endDate": modalReserveForm.find('.endDateField').val(),
            "coffee": modalReserveForm.find('.coffeeField').prop('checked'),
            "numberOfPeople": modalReserveForm.find('.numberOfPeopleField').val()
        });
    }                
        
        btnOpenModal.off().click(function(ev){
            ev.preventDefault();
            modal.find('.modal-title').html('Nova reserva');
            clearModalFields();
            modal.find('.btnModalSave').show();
            modal.find('.btnModalUpdate').hide();
            modal.modal('show');
    });

    //define os eventos dos botões da página				
    btnDeleteList.off().click(function(ev){
        ev.preventDefault();
        $this = $(this);
        
        $('table tbody tr').remove();            
    });

        btnEdit.off().click(function(ev){
            ev.preventDefault();
            $this = $(this);
            modal.find('.reserveIdField').val(btnEdit.attr('data-id'));
            modal.find('.btnModalUpdate').show();
            modal.find('.btnModalSave').hide();
            modal.modal('show');
        });

        btnDelete.off().click(function(ev){
            ev.preventDefault();
            $this = $(this);
            var url = urlToDelete.replace('{id}', $this.attr('data-id'));                        
            
            var invocation = new XMLHttpRequest();
            invocation.open('DELETE', url, true);
            invocation.withCredentials = false;
            invocation.onreadystatechange  = function(){
                if(invocation.readyState === 4 && invocation.status === 200){                                        
                    getGridData();
                    alert("Exclusão realizada com sucesso!");                        
                }                
            },
            invocation.send();            
    });

    modal.on("click", ".btnModalUpdate", function(ev){
            var url = urlToUpdate.replace('{id}', modal.find('.reserveIdField').val());
            $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: url,
                    data: modalFormToJson(),
                    success: function(response){
                            alert(response);
                    } 
            });
            modal.modal('hide');
        });
/*
        $('.btnTeste').click(function(ev){
            $.ajax({
              dataType: "json",
              url: urlToGetList,
              data: {},					  
              contentType: 'application/json',
              beforeSend: function (xhr) {
                            xhr.withCredentials = false;
              },
              success: function(response){
                    alert("Olá ajax");
              },
              error: function(response){
                    alert('ERROR');
              }
            });											
        });
*/
/*
        $('.btnTeste2').click(function(ev){

            var invocation = new XMLHttpRequest();
            var url = urlToGetList;
            invocation.open('GET', url, true);
            invocation.withCredentials = false;
            invocation.onreadystatechange  = function(){
                    if(invocation.readyState === 4 && invocation.status === 200){
                            var json = invocation.response;
                            json = typeof json === 'string' ? JSON.parse(json) : json;
                            $('table tbody tr').remove();
                            for(var i = 0; i < json.length; i++){

                                    var line = '<tr data-id="'+json[i].id+'">';
                                            line += '<td><input type="hidden" value="'+json[i].id+'"></input></td>';
                                            line += '<td>';
                                            line += '<input type="checkbox" value="false" data-id="'+json[i].id+'"/>';
                                            line += '</td>';
                                            line += '<td><span>'+json[i].requester+'</span></td>';
                                            line += '<td><span>'+json[i].meetingRoom.name+'</span></td>';
                                            line += '<td><span>'+json[i].meetingRoom.multimediaResources == true ? 'Sim' : 'Não' + '</span></td>';
                                            line += '<td><span>'+json[i].meetingRoom.capacity+'</span></td>';				
                                            line += '<td><span>'+json[i].startDate+'</span></td>';
                                            line += '<td><span>'+json[i].endDate+'</span></td>';
                                            line += '<td><span>'+json[i].withCoffeeBreak == true ? 'Sim' : 'Não' +'</span></td>';
                                            line += '<td><span>'+json[i].numberOfPeople+'</span></td>';
                                            line += '<td>';
                                            line += '<form>';
                                            line += '<button class="btn btn-warning btn-xs btnEdit" data-id="'+json[i].id+'">Editar</button>';
                                            line += '<button class="btn btn-danger btn-xs btnDelete" data-id="'+json[i].id+'">Remover</button>';
                                            line += '</form>';
                                            line += '</td>';
                                            line += '</tr>';
                                    $('table tbody').append(line);
                            }
                    }
            };
            //invocation.onreadystatechange = handler;
            invocation.send();				
        });
*/
        //eventos dos elementos do modal
        modal.on("click",".btnModalSave", function(ev){
            if(checkModalFields()){
                var invocation = new XMLHttpRequest();
                invocation.open('POST', urlToInsert, true);
                invocation.withCredentials = false;
                invocation.onreadystatechange  = function(){
                    if(invocation.readyState === 4 && invocation.status === 200){
                        getGridData();
                        modal.modal('hide');
                        alert("Reserva realizada com sucesso!");
                    }                
                },
                invocation.send(modalFormToJson());
            }else{
                alert('Os campos solicitante, início e fim são obrigatórios!');
            }
        });

        modal.on("change",".checkCoffe", function(ev){
            $this = $(this);
            if($this.prop('checked')){
                    modal.find('.numberOfPeopleElement').show();
            }else{
                    modal.find('.numberOfPeopleElement').hide();
            }
        });				
        
        modal.on("change", ".placeIdField", function(ev){
            $this = $(this);
            var field = $('.meetingRoomField');
            getRoomsData(field.val());
            
            //pegar a opção ativa
            //obter o id do place
            /*var invocation = new XMLHttpRequest();
            invocation.open('GET', url, true);
            invocation.withCredentials = false;
            invocation.onreadystatechange  = function(){
                if(invocation.readyState === 4 && invocation.status === 200){                                                            
                    getRoomsData(1);//TODO: pegar o id da opção selecionada
                }                
            },
            invocation.send();            */
            
        });
        
        getGridData();
        getPlacesData();

});