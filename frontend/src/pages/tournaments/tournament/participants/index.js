import React, { useCallback, useEffect, useState } from "react";
import { Button, Form, InputGroup, Row, FloatingLabel } from 'react-bootstrap';
import { Input } from "react-bootstrap-typeahead";
import { addParticipants, getParticipants } from "../../../../services/tournamentService";
import { PaginatedTable } from "../../../../shared/components/paginatedTable";
import { ToastContainer, toast } from 'react-toastify';

export default function Participants({id , action}){

    const [participant, setParticipant] = useState("");
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
        
        //const offset = ((page - 1) * pageSize);
        //page = 1
        getParticipants(id, 1, pageSize).then(
            response => {
                if(response.status === 200){
                    setData({
                        elements: response.data.response,
                        count: 100,
                    });
                }
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

    const handleSubmit = useCallback((event) => {
        event.preventDefault();
        event.stopPropagation();
    
        if(!validated){
            setValidated(validated => !validated);
        }
        
        addParticipants(id, {username:participant}).then(
            async response => {
               if(response.status === 200){
                   await getData(1,pageSize);
               } else {

               }
            }
        )
    });

    const handleDataFormat = useCallback((participant) => {
        return participant.name
    });

    return(
        <>
            {
                action === 'edit' ? 
                <Row>
                    <Form onSubmit={handleSubmit} noValidate validated={validated}>
                        <Form.Group className='_6lux' controlId="formParticipantAdd">
                            <InputGroup>
                                <FloatingLabel className='group-first-element'>
                                            <Form.Control name="participant" type="text" placeholder="Add participant" required
                                                value={participant} 
                                                onChange={(event) => {setParticipant(event.target.value)}}/>
                                            <Form.Text className="text-muted">
                                            </Form.Text>
                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Add participant</label>   
                                </FloatingLabel>
                                <Button type={'submit'}>Add</Button>
                            </InputGroup>
                        </Form.Group>
                    </Form>
                </Row>
                :<></>
            }
            {
                data.count > 0 ? 
                <>
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
                :
                <h3>
                    NO SIGNED PARTICIPANTS
                </h3>
            }
            <ToastContainer/>
        </>
    );
}