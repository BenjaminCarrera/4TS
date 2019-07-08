/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { PermisoAuthorityDeleteDialogComponent } from 'app/entities/permiso-authority/permiso-authority-delete-dialog.component';
import { PermisoAuthorityService } from 'app/entities/permiso-authority/permiso-authority.service';

describe('Component Tests', () => {
  describe('PermisoAuthority Management Delete Component', () => {
    let comp: PermisoAuthorityDeleteDialogComponent;
    let fixture: ComponentFixture<PermisoAuthorityDeleteDialogComponent>;
    let service: PermisoAuthorityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [PermisoAuthorityDeleteDialogComponent]
      })
        .overrideTemplate(PermisoAuthorityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermisoAuthorityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermisoAuthorityService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
