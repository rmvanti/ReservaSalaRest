$(document).ready(function(){
			
    //definição das URL's dos serviços
    var baseURL = 'http://localhost:8084/ReservaSalaREST/rest';
    
    var urlToInsert      = baseURL + "/reserve";
    var urlToGetList     = baseURL + "/reserve";
    var urlToDeleteList  = baseURL + "/reserve";
    
    var urlToGetById = baseURL + "/reserve" + '/{id}';
    var urlToDelete  = baseURL + "/reserve" + '/{id}';    
    var urlToUpdate  = baseURL + "/reserve" + '/{id}';
    
    var urlToGetPlaceList = baseURL + '/place';
    var urlToGetRoomList  = baseURL + '/meetingRoom' + '/{placeId}';   

    var btnOpenModal = $('.btnOpenModal');
    var btnDeleteList = $('.btnDeleteList');    

    var modal = $('#reserveCrudModal');
    var modalReserveForm = modal.find('#reserveForm');
    
    var modalConfirmDelete = $('#confirmDeleteModal');
        
    var tbody = $('table').find('tBody');

    function getRoomsData(placeId){                    
        var field = $('.meetingRoomField');
        var invocation = new XMLHttpRequest();
        var url = urlToGetRoomList.replace('{placeId}', placeId);
        invocation.open('GET', url, true);
        invocation.withCredentials = false;
        invocation.onreadystatechange  = function(){
            if(invocation.readyState === 4 && invocation.status === 200){
                var json = invocation.response;
                var line = '';
                var first = true;
                json = typeof json === 'string' ? JSON.parse(json) : json;
                field.find('option').remove();
                for(var i = 0; i < json.length; i++){
                    if(first){
                        line = '<option value="' + json[i].id  + '" selected >' + json[i].name +'</option>';
                        first = false;
                    }else{
                        line = '<option value="' + json[i].id  + '">' + json[i].name +'</option>';
                    }
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
                    line += '<input type="checkbox" class="rowCheck" data-id="'+json[i].id+'"/>';
                    line += '</td>';
                    line += '<td><span>'+json[i].requester+'</span></td>';
                    line += '<td><span>'+json[i].meetingRoom.name+'</span></td>';
                    line += '<td><span>'+json[i].meetingRoom.description+'</span></td>';
                    line += json[i].meetingRoom.multimediaResources === true ? '<td><span>Sim</span></td>' : '<td><span>Não</span></td>';
                    line += '<td><span>'+json[i].meetingRoom.capacity+'</span></td>';			
                    line += '<td><span>'+json[i].startDate+'</span></td>';
                    line += '<td><span>'+json[i].endDate+'</span></td>';
                    line += json[i].description === undefined || json[i].description === null ?'<td><span></span></td>' : '<td><span>'+json[i].description+'</span></td>' ;
                    //line += json[i].withCoffeeBreak === true ? '<td><span>Sim</span></td>' : '<td><span>Não</span></td>';
                    //line += '<td><span>'+json[i].numberOfPeople+'</span></td>';
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
        modal.find(".descriptionField").val('');
        modal.find(".coffeeField").prop('checked', false);
        modal.find(".numberOfPeopleField").val('');
        modal.find(".numberOfPeopleElement").hide();
    };
    
    function loadModalForm(json){
        modal.find(".placeIdField").val(json.meetingRoom.place.id);
        modal.find(".meetingRoomField").val(json.meetingRoom.id);       
        modal.find(".reserveIdField").val(json.id);
        modal.find(".requesterField").val(json.requester);
        
        
        modal.find(".startDateField").value = json.startDate;                
        modal.find(".endDateField").value = json.endDate;
        
        var desc = json.description === undefined || json.description === null ? "" : json.description; 
        modal.find(".descriptionField").val(desc);
        
        modal.find(".coffeeField").prop('checked', json.withCoffeeBreak);
        modal.find(".numberOfPeopleField").val(json.numberOfPeople);        
    }
    
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
            "numberOfPeople": modalReserveForm.find('.numberOfPeopleField').val(),
            "description": modalReserveForm.find('.descriptionField').val()
        });
    }                
        
    btnOpenModal.off().click(function(ev){
        ev.preventDefault();
        modal.find('.modal-title').html('Nova reserva');
        clearModalFields();
        getPlacesData();
        modal.find('.btnModalSave').show();
        modal.find('.btnModalUpdate').hide();
        modal.modal('show');
    });

    tbody.on("click",".btnDelete", function(ev){ 
        ev.preventDefault();
        $this = $(this);
        modalConfirmDelete.find('.confirmDeleteById').val($this.attr('data-id'));
        modalConfirmDelete.find('.confirmDeleteList').val('');
        modalConfirmDelete.modal('show');        
    });
    
    tbody.on('click', '.btnEdit', function(ev){
        ev.preventDefault();
        $this = $(this);
        var url = urlToGetById.replace('{id}', $this.attr('data-id'));
        
        var invocation = new XMLHttpRequest();
        invocation.open('GET', url, true);
        invocation.withCredentials = false;
        invocation.onreadystatechange  = function(){
            if(invocation.readyState === 4 && invocation.status === 200){
                var json = invocation.response;                
                json = typeof json === 'string' ? JSON.parse(json) : json;
                loadModalForm(json);
                modal.find('.btnModalSave').hide();
                modal.find('.btnModalUpdate').show();
                modal.modal('show');
            }
        },
        invocation.send();
    });
    
    btnDeleteList.off().click(function(ev){
        ev.preventDefault();
        var rows = tbody.find('.rowCheck');
        var ids = '';
        var item;
        for(var i = 0; i < rows.length; i++){
            item = $(rows[i]);
            if(item.prop('checked')){
                ids += item.attr('data-id')+",";
            }
        }
        ids = ids.substring(0,(ids.length-1));        
        if(ids === ''){
            alert("Por favor selecione as reservas que devem ser excluídas!");
        }else{
            modalConfirmDelete.find('.confirmDeleteById').val('');
            modalConfirmDelete.find('.confirmDeleteList').val(ids);
            modalConfirmDelete.modal('show');                    
        }
    });
    
    //eventos dos elementos do modal
    modal.on("click",".btnModalSave", function(ev){
        ev.preventDefault();
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
        ev.preventDefault();
        $this = $(this);
        if($this.prop('checked')){
                modal.find('.numberOfPeopleElement').show();
        }else{
                modal.find('.numberOfPeopleElement').hide();
        }
    });
    
    modal.on("click", ".btnModalUpdate", function(ev){
        ev.preventDefault();
        var url = urlToUpdate.replace('{id}', modal.find('.reserveIdField').val());
        var invocation = new XMLHttpRequest();
        invocation.open('PUT', url, true);
        invocation.withCredentials = false;
        invocation.onreadystatechange  = function(){
            if(invocation.readyState === 4 && invocation.status === 200){
                getGridData();           
                modal.modal('hide');
                alert("Reserva atualizada com sucesso!");
            }                
        },
        invocation.send(modalFormToJson());        
    });
    
    modal.on("change", ".placeIdField", function(ev){
        ev.preventDefault();
        $this = $(this);
        var field = $('.placeIdField');
        getRoomsData(field.val());

    });
    
    modalConfirmDelete.on('click', '.btnConfirmDelete', function(ev){
        ev.preventDefault();        
        if(modalConfirmDelete.find('.confirmDeleteById').val() !== ''){
            var url = urlToDelete.replace('{id}', modalConfirmDelete.find('.confirmDeleteById').val());
            var invocation = new XMLHttpRequest();
            invocation.open('DELETE', url, true);
            invocation.withCredentials = false;
            invocation.onreadystatechange  = function(){
                if(invocation.readyState === 4 && invocation.status === 200){
                    getGridData();
                    alert("Reserva excluída com sucesso!");
                    modalConfirmDelete.modal('hide');
                }
            },
            invocation.send();        
        }else{
            var invocation = new XMLHttpRequest();
            invocation.open('DELETE', urlToDeleteList, true);
            invocation.withCredentials = false;
            invocation.onreadystatechange  = function(){
                if(invocation.readyState === 4 && invocation.status === 200){
                    getGridData();
                    alert("Reservas excluídas com sucesso!");
                    modalConfirmDelete.modal('hide');
                }
            },
            invocation.send(modalConfirmDelete.find('.confirmDeleteList').val());
        }
        
    });
                    				                        
    getGridData();
    getPlacesData();
    
});