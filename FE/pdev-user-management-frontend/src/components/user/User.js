import React, { useState } from 'react'
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CFormFeedback,
  CRow,
  CFormCheck,
  CFormSelect,
  CInputGroup,
  CInputGroupText,
  CTableHeaderCell,
  CTableHead,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableRow,
} from '@coreui/react'

const User = () => {
  const [validated, setValidated] = useState(false)
  const handleSubmit = (event) => {
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
    }

    const selects = form.querySelectorAll('select')
    selects.forEach((select) => {
      if (select.value === '-1') {
        select.setCustomValidity('Please select an option.')
      } else {
        select.setCustomValidity('')
      }
    })
    setValidated(true)
  }
  return (
    <CRow>
      <CCol xs={12}>
        <CCard className="mb-4">
          <CCardHeader>
            <strong>Manage Users</strong>
          </CCardHeader>
          <CCardBody>
            <CForm
              className="row gx-3 gy-2 align-items-center"
              noValidate
              validated={validated}
              onSubmit={handleSubmit}
            >
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputName">First Name</CFormLabel>
                <CFormInput id="specificSizeInputName" placeholder="First Name" required />
                <CFormFeedback tooltip invalid>
                  Please provide a first name.
                </CFormFeedback>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Last Name</CFormLabel>
                <CInputGroup>
                  <CFormInput
                    id="specificSizeInputGroupUsername"
                    placeholder="Last Name"
                    required
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide a last name.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">User Name</CFormLabel>
                <CInputGroup className="has-validation">
                  <CInputGroupText>@</CInputGroupText>
                  <CFormInput
                    id="specificSizeInputGroupUsername"
                    placeholder="User Name"
                    required
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide a user name.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Password</CFormLabel>
                <CInputGroup>
                  <CFormInput
                    type="password"
                    id="specificSizeInputGroupUsername"
                    placeholder="Password"
                    required
                  />
                  <CFormFeedback tooltip invalid>
                    Please provide a password.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Email</CFormLabel>
                <CInputGroup>
                  <CFormInput id="specificSizeInputGroupUsername" placeholder="Email" required />
                  <CFormFeedback tooltip invalid>
                    Please provide an email.
                  </CFormFeedback>
                </CInputGroup>
              </CCol>
              <CCol sm={4}>
                <CFormLabel htmlFor="specificSizeSelect">Status</CFormLabel>
                <CFormSelect id="specificSizeSelect" style={{ cursor: 'pointer' }} required>
                  <option value="-1">Select a status</option>
                  <option value="Active">Active</option>
                  <option value="In_active">In-active</option>
                </CFormSelect>
                <CFormFeedback tooltip invalid>
                  Please select a status.
                </CFormFeedback>
              </CCol>
              <CCol sm={2}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Email verification</CFormLabel>
                <CInputGroup>
                  <CFormCheck
                    type="checkbox"
                    id="autoSizingCheck2"
                    label="Verified"
                    style={{ cursor: 'pointer' }}
                  />
                </CInputGroup>
              </CCol>
              <CCol sm={2}>
                <CFormLabel htmlFor="specificSizeInputGroupUsername">Lock user account</CFormLabel>
                <CInputGroup>
                  <CFormCheck
                    type="checkbox"
                    id="autoSizingCheck2"
                    label="Locked"
                    style={{ cursor: 'pointer' }}
                  />
                </CInputGroup>
              </CCol>
              <br />
              <br />
              <br />
              <br />
              <CCol xs={12}>
                <CCard className="mb-4">
                  <CCardHeader>
                    <span>Authorize Parties</span>
                  </CCardHeader>
                  <CCardBody>
                    <CCol sm={6}>
                      <CInputGroup>
                        <CFormCheck
                          type="checkbox"
                          id="autoSizingCheck2"
                          label="Verified"
                          style={{ cursor: 'pointer' }}
                        />{' '}
                        &nbsp;&nbsp;
                        <CFormCheck
                          type="checkbox"
                          id="autoSizingCheck2"
                          label="Verified"
                          style={{ cursor: 'pointer' }}
                        />
                      </CInputGroup>
                    </CCol>
                  </CCardBody>
                </CCard>
              </CCol>
              <br />
              <br />
              <br />
              <br />
              <CCol xs={12}>
                <CCard className="mb-4">
                  <CCardHeader>
                    <span>Application & Roles</span>
                  </CCardHeader>
                  <CCardBody>
                    <div
                      className="row gx-3 gy-2 align-items-center"
                      noValidate
                      validated={validated}
                      onSubmit={handleSubmit}
                    >
                      <CCol sm={4}>
                        <CFormLabel htmlFor="specificSizeSelect">Application Scope</CFormLabel>
                        <CFormSelect id="specificSizeSelect" style={{ cursor: 'pointer' }} required>
                          <option value="-1">Select an application scope</option>
                        </CFormSelect>
                        <CFormFeedback tooltip invalid>
                          Please select an application scope.
                        </CFormFeedback>
                      </CCol>
                      <CCol sm={4}>
                        <CFormLabel htmlFor="specificSizeSelect">User Role</CFormLabel>
                        <CFormSelect id="specificSizeSelect" style={{ cursor: 'pointer' }} required>
                          <option value="-1">Select a user role</option>
                        </CFormSelect>
                        <CFormFeedback tooltip invalid>
                          Please select a user role.
                        </CFormFeedback>
                      </CCol>
                      <CCol sm={4}>
                        <CCol xs="auto" style={{ marginTop: '40px' }}>
                          <CButton type="button" class="btn btn-success btn-sm">
                            <strong style={{ color: 'white' }}>+</strong>
                          </CButton>
                        </CCol>
                      </CCol>
                      <br />
                      <br />
                      <br />
                      <br />
                      <CCol xs={12}>
                        <CTable>
                          <CTableHead color="dark">
                            <CTableRow>
                              <CTableHeaderCell scope="col">Scope</CTableHeaderCell>
                              <CTableHeaderCell scope="col">Role</CTableHeaderCell>
                              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
                            </CTableRow>
                          </CTableHead>
                          <CTableBody>
                            <CTableRow>
                              <CTableDataCell>Mark</CTableDataCell>
                              <CTableDataCell>Otto</CTableDataCell>
                              <CTableDataCell>
                                <a
                                  href="#"
                                  class="btn btn-danger btn-sm"
                                  style={{ color: 'white' }}
                                >
                                  Remove
                                </a>
                              </CTableDataCell>
                            </CTableRow>
                          </CTableBody>
                        </CTable>
                      </CCol>
                    </div>
                  </CCardBody>
                </CCard>
              </CCol>
              <CCol xs={11} />
              <CCol xs="auto">
                <CButton color="primary" type="submit">
                  Create
                </CButton>
              </CCol>
            </CForm>
          </CCardBody>
        </CCard>
      </CCol>
      <CCol xs={12}>
        <CTable>
          <CTableHead color="dark">
            <CTableRow>
              <CTableHeaderCell scope="col">First name</CTableHeaderCell>
              <CTableHeaderCell scope="col">Last name</CTableHeaderCell>
              <CTableHeaderCell scope="col">User name</CTableHeaderCell>
              <CTableHeaderCell scope="col">Email</CTableHeaderCell>
              <CTableHeaderCell scope="col">Email verify</CTableHeaderCell>
              <CTableHeaderCell scope="col">Locked</CTableHeaderCell>
              <CTableHeaderCell scope="col">Status</CTableHeaderCell>
              <CTableHeaderCell scope="col">Action</CTableHeaderCell>
            </CTableRow>
          </CTableHead>
          <CTableBody>
            <CTableRow>
              <CTableDataCell>Mark</CTableDataCell>
              <CTableDataCell>Otto</CTableDataCell>
              <CTableDataCell>@mdo</CTableDataCell>
              <CTableDataCell>Mark</CTableDataCell>
              <CTableDataCell>Otto</CTableDataCell>
              <CTableDataCell>@mdo</CTableDataCell>
              <CTableDataCell>@mdo</CTableDataCell>
              <CTableDataCell>@mdo</CTableDataCell>
            </CTableRow>
          </CTableBody>
        </CTable>
      </CCol>
    </CRow>
  )
}

export default User
