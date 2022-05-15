import React, { useCallback, useEffect, useState } from "react";
import { Button, Form, InputGroup, Row, FloatingLabel } from 'react-bootstrap';
import { Input } from "react-bootstrap-typeahead";
import { getParticipants } from "../../../../services/tournamentService";
import { PaginatedTable } from "../../../../shared/components/paginatedTable";
import SearchMultipleAutocomplete from "../../../../shared/components/selectAutocomplete";
import { useHandleHttpResponse } from "../../../../shared/hooks/responseHandlerHook";

export default function Participants({id , action}){

    const [participants, setParticipants] = useState([]);
    const pageSize = 5;
    const [data, setData] = useState({
        elements: [],
        count: 10,
    });
    const [validated, setValidated] = useState(false);
    const [valid, setValid] = useState(true);

    const headings = [
        {   
            name: 'name',
            show: 'Name'
        }
    ];

    const getData = async (page, pageSize) =>  {
        
        const offset = ((page - 1) * pageSize);
        
        getParticipants(id, offset, pageSize).then(
            response => {//HAY QUE CAMBIAR LA RESPUESTA
                const handled = useHandleHttpResponse(() => {
                    setData(response.data);
                }, response.status);

                handled();
            }
        );
        
        /*
            const elements = [];
        for(let i = offset + 1; i < offset + pageSize + 1; i++){
            elements.push({
                id: i,
                name: `asdsda`
            });
        }

        const mock = {
            elements : elements,
            count : 100,
        }

        setData(mock);
        */
    };

    useEffect(() => {
        const init = async () => {
            await getData(1, pageSize);
        };
        init();
    }, []);

    const handlePageChange = useCallback(async (page, pageSize) => {
        await getData(page, pageSize);
    });

    const handleRowClick = useCallback((element) => {        
    });

    
    const handleSearch = useCallback(async (search) => {
        //return getParticipants(id);
        console.log(search)
        return {
            data: [
                {
                    name: 'a',
                    id: 1
                },
                {
                    name: 'b',
                    id: 2
                },
                {
                    name: 'c',
                    id: 3
                }
            ]
        }
    });
    
    const handleSubmit = useCallback((event) => {
        event.preventDefault();
        event.stopPropagation();
    
        if(!validated){
            setValidated(validated => !validated);
        }
        
        if(participants.length === 0){
            setValid(valid => false);
            return;
        }
    });

    const handleDataFormat = useCallback((participant) => {
        return participant.name
    });

    return(
        <>
            {
                true ? 
                <Row>
                    <Form onSubmit={handleSubmit} noValidate validated={validated}>
                        <Form.Group className='_6lux' controlId="formParticipantAdd">
                            <InputGroup>
                                <SearchMultipleAutocomplete
                                        validated={validated}
                                        valid={valid}
                                        dataFormater={handleDataFormat}
                                        onSearch={handleSearch}
                                        onSelection={(selection) => {setParticipants(selection)}}
                                        required={true}
                                        placeholder={'Add Participants'}
                                />                   
                                <Button type={'submit'}>Add</Button>
                            </InputGroup>
                        </Form.Group>
                    </Form>
                </Row>
                :<></>
            }
            <PaginatedTable 
                data={data}
                headings={headings}
                onPageChange={handlePageChange}
                onClick={handleRowClick}
                hover={false}
                key={'participants-table'}
                pageSize={pageSize}
            />
        </>
    );
}