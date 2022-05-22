import React, {useState, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import { Row } from "react-bootstrap";
import { getPositions } from "../../services/tournamentService";
import { useParams } from "react-router-dom";
import NonPaginatedTable from "../../shared/components/table";
import { ToastContainer, toast } from 'react-toastify';

export default function Positions(){
    const {id} = useParams();
    const [data, setData] = useState({
        elements: []
    });
    
    const headings = [
        {
            name: 'position',
            show: '#',
        },
        {
            name: 'name',
            show: 'Name',
        },
        {
            name: 'points',
            show: 'Points',
        }
    ];

    const getData = () =>  {
        getPositions(id).then(
            response => {
                setData({
                    elements: Array.from(response.data.response).map( (p, i) => {
                        return {
                            position: i + 1,
                            name: p.user['username'],
                            points: p.points
                        }
                    }),
                });
            }
        ).catch(
            e => {
                console.log(e.response.data);
                toast.error(e.response.data.response.message);
            }
        );        
    };

    useEffect(() => {
        const init = async () => {
            getData();
        };

        init();
    }, []);

    const handleRowClick = useCallback((element) => {
    });

    return (
        <>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card>
                        <Card.Body>
                            <Card.Title>TOP 10</Card.Title>
                            <Row>
                                {
                                    data.elements.length > 0 ?
                                    <>
                                        <NonPaginatedTable 
                                            headings={headings}
                                            data={data}
                                            onClick={handleRowClick}
                                            hover={false}
                                            key='positions'
                                        />
                                    </>
                                    :
                                    <h3>NO SIGNED PARTICIPANTS</h3>
                                }   
                            </Row>                                                     
                        </Card.Body>
                    </Card>
                    <ToastContainer/>
                </Container>
            </Col>
        </>
    )
}
