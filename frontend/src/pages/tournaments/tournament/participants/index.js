import React, { useCallback, useEffect, useState } from "react";
import { Button, Form, InputGroup, Row, FloatingLabel } from 'react-bootstrap';
import { Input } from "react-bootstrap-typeahead";
import { addParticipants, getParticipants, getPositions } from "../../../../services/tournamentService";
import { PaginatedTable } from "../../../../shared/components/paginatedTable";
import { ToastContainer, toast } from 'react-toastify';
import NonPaginatedTable from "../../../../shared/components/table";

export default function Participants({id , action}){

    const [participant, setParticipant] = useState("");
    const [data, setData] = useState({
        elements: [],
    });
    const [validated, setValidated] = useState(false);

    const headings = [
        {   
            name: 'name',
            show: 'Name'
        }
    ];

    const getData = () =>  {
        getPositions(id).then(
            response => {
                setData({
                    elements: Array.from(response.data.response).map((p, i) => (
                        {
                            id: i,
                            name: p.user.username,
                        }
                    )),
                });
            }
        ).catch( e => {
                toast.error(e.response.data.response.message);
            })
    };

    useEffect(() => {
        const init = () => {
            getData();
        };
        init();
    }, []);


    const handleRowClick = useCallback((element) => {        
    });

    const handleSubmit = useCallback((event) => {
        event.preventDefault();
        event.stopPropagation();
    
        if(!validated){
            setValidated(validated => !validated);
        }
        
        if(participant === ''){
            toast.error('Complete fields');
            return;
        }

        addParticipants(id, {username:participant}).then(
            response => {
                getData();
                toast.success("participante agregado correctamente")
            }
        ).catch( e => {
                toast.error(e.response.data.response.message);
            })
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
                data.elements.length > 0 ? 
                <NonPaginatedTable 
                    data={data}
                    headings={headings}
                    onClick={handleRowClick}
                    hover={false}
                    key={'participants-table'}
                />
                :
                <h3>
                    NO SIGNED PARTICIPANTS
                </h3>
            }
            <ToastContainer/>
        </>
    );
}